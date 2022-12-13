#define _DEFAULT_SOURCE

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "mem_internals.h"
#include "mem.h"
#include "util.h"

void debug_block(struct block_header* b, const char* fmt, ... );
void debug(const char* fmt, ... );

extern inline block_size size_from_capacity( block_capacity cap );
extern inline block_capacity capacity_from_size( block_size sz );

static bool            block_is_big_enough( size_t query, struct block_header* block ) { return block->capacity.bytes >= query; }
static size_t          pages_count   ( size_t mem )                      { return mem / getpagesize() + ((mem % getpagesize()) > 0); }
static size_t          round_pages   ( size_t mem )                      { return getpagesize() * pages_count( mem ) ; }

static void block_init( void* restrict addr, block_size block_sz, void* restrict next ) {
  *((struct block_header*)addr) = (struct block_header) {
    .next = next,
    .capacity = capacity_from_size(block_sz),
    .is_free = true
  };
}

static size_t region_actual_size( size_t query ) { return size_max( round_pages( query ), REGION_MIN_SIZE ); }

extern inline bool region_is_invalid( const struct region* r );



static void* map_pages(void const* addr, size_t length, int additional_flags) {
  return mmap( (void*) addr, length, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS | additional_flags , -1, 0 );
}

/*  аллоцировать регион памяти и инициализировать его блоком */
static struct region alloc_region  ( void const * addr, size_t query ) {

  size_t capacity_size = size_from_capacity((block_capacity){query}).bytes;
  size_t region_size =region_actual_size(capacity_size);

  void* mapped_addr = map_pages(addr, region_size, MAP_FIXED_NOREPLACE);

  mapped_addr = (mapped_addr == MAP_FAILED) ? map_pages(addr, region_size, 0) : mapped_addr;
  bool is_addr = (mapped_addr == addr);
  if(mapped_addr == MAP_FAILED) return REGION_INVALID;

  struct region result_region = {mapped_addr, region_size, is_addr};

  if(region_is_invalid(&result_region)) return REGION_INVALID;

  block_init(mapped_addr, (block_size){result_region.size}, NULL);
  return result_region;
}

static void* block_after( struct block_header const* block )         ;

void* heap_init( size_t initial ) {
  const struct region region = alloc_region( HEAP_START, initial );
  if ( region_is_invalid(&region) ) return NULL;

  return region.addr;
}

#define BLOCK_MIN_CAPACITY 24

/*  --- Разделение блоков (если найденный свободный блок слишком большой )--- */

static bool block_splittable( struct block_header* restrict block, size_t query) {
  return block-> is_free && query + offsetof( struct block_header, contents ) + BLOCK_MIN_CAPACITY <= block->capacity.bytes;
}

static bool split_if_too_big( struct block_header* block, size_t query ) {
  if(block_splittable(block, query)) {
    void* second_part_block = (block->contents + query);
    block_init(second_part_block, (block_size){block->capacity.bytes - query}, block->next);
    block->next = second_part_block;
    block->capacity.bytes = query;
    return true;
  }
  return false;
}


/*  --- Слияние соседних свободных блоков --- */

static void* block_after( struct block_header const* block )              {
  return  (void*) (block->contents + block->capacity.bytes);
}
static bool blocks_continuous (
                               struct block_header const* fst,
                               struct block_header const* snd ) {
  return (void*)snd == block_after(fst);
}

static bool mergeable(struct block_header const* restrict fst, struct block_header const* restrict snd) {
  return fst->is_free && snd->is_free && blocks_continuous( fst, snd ) ;
}

static bool try_merge_with_next( struct block_header* block ) {
  if(block->next != NULL && mergeable(block, block->next)){
    block->capacity.bytes += size_from_capacity((block->next)->capacity).bytes;
    block->next = (block->next)->next;
    return true;
  }
  return false;
}


/*  --- ... ecли размера кучи хватает --- */

struct block_search_result {
  enum {BSR_FOUND_GOOD_BLOCK, BSR_REACHED_END_NOT_FOUND, BSR_CORRUPTED} type;
  struct block_header* block;
};


static struct block_search_result find_good_or_last  ( struct block_header* restrict block, size_t sz )    {
  
  if (!block) return (struct block_search_result) {BSR_CORRUPTED, NULL};
  
  struct block_search_result search_result = {BSR_FOUND_GOOD_BLOCK, NULL};
  while(true){
    search_result.block = block;
    if(block->is_free){
      while(try_merge_with_next(block));
      if(block_is_big_enough(sz, block)) return search_result;
    }
    if (!block->next) break;
    block = block-> next;
  }
  
  return (struct block_search_result){BSR_REACHED_END_NOT_FOUND, block};
}

/*  Попробовать выделить память в куче начиная с блока `block` не пытаясь расширить кучу
 Можно переиспользовать как только кучу расширили. */
static struct block_search_result try_memalloc_existing ( size_t query, struct block_header* block ) {
  struct block_search_result result = find_good_or_last(block, query);
  if(result.type == BSR_FOUND_GOOD_BLOCK) {
    split_if_too_big(result.block, query);
    result.block->is_free = false;
  }
  return result;
}



static struct block_header* grow_heap( struct block_header* restrict last, size_t query ) {
  if(!last) return NULL;
  struct region allocated_region = alloc_region(block_after(last), query);
  struct block_header* result_block = allocated_region.addr;
  last->next = result_block;
  if (allocated_region.extends && last->is_free) {
    try_merge_with_next(last);
    return last;
  }

  return result_block;
}

/*  Реализует основную логику malloc и возвращает заголовок выделенного блока */
static struct block_header* memalloc( size_t query, struct block_header* heap_start) {
  query = size_max(query, BLOCK_MIN_CAPACITY);

  struct block_search_result search_result = try_memalloc_existing(query, heap_start);
  if(search_result.type == BSR_CORRUPTED) return NULL;

  if(search_result.type == BSR_REACHED_END_NOT_FOUND) {
    grow_heap(search_result.block, query);
    search_result = try_memalloc_existing(query, heap_start);
  }

  if (search_result.type == BSR_FOUND_GOOD_BLOCK) {
    return search_result.block;
  }
  return NULL;
}

void* _malloc( size_t query ) {
  struct block_header* const addr = memalloc( query, (struct block_header*) HEAP_START );
  if (addr) return addr->contents;
  else return NULL;
}

static struct block_header* block_get_header(void* contents) {
  return (struct block_header*) (((uint8_t*)contents)-offsetof(struct block_header, contents));
}

void _free( void* mem ) {
  if (!mem) return ;
  struct block_header* header = block_get_header( mem );
  header->is_free = true;
  while (try_merge_with_next(header));
}
