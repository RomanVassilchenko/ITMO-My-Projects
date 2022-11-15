/* heap-1.c */

#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

#define HEAP_BLOCKS 16
#define BLOCK_CAPACITY 1024

enum block_status { BLK_FREE = 0, BLK_ONE, BLK_FIRST, BLK_CONT, BLK_LAST };

struct heap {
  struct block {
    char contents[BLOCK_CAPACITY];
  } blocks[HEAP_BLOCKS];
  enum block_status status[HEAP_BLOCKS];
} global_heap = {0};

struct block_id {
    size_t ind;
  size_t       value;
  bool         valid;
  struct heap* heap;
};

struct block_id block_id_new(size_t value, struct heap* from) {
  return (struct block_id){.valid = true, .value = value, .heap = from};
}
struct block_id block_id_invalid() {
  return (struct block_id){.valid = false};
}

bool block_id_is_valid(struct block_id bid) {
  return bid.valid && bid.value < HEAP_BLOCKS;
}

/* Find block */

bool block_is_free(struct block_id bid) {
  if (!block_id_is_valid(bid))
    return false;
  return bid.heap->status[bid.value] == BLK_FREE;
}

/* Allocate */
//? ? ?
struct block_id block_allocate(struct heap* heap, size_t size) {
  if(size == 1){
      for (size_t i = 0; i < HEAP_BLOCKS; i++) {
          if (heap->status[i] == BLK_FREE) {
              heap->status[i] = BLK_ONE;
              struct block_id result = block_id_new(size, heap);
              result.ind = i;
              return result;
          }
      }
  }
  else{
      size_t startInd = 0;
      size_t currSize = 0;
      for(size_t i = 0; i < HEAP_BLOCKS; i++){
          if (heap->status[i] == BLK_FREE && currSize == 0){
              startInd = i;
              currSize = 1;
          } else if(heap->status[i] == BLK_FREE){
              currSize ++;
              if(currSize == size){
                  heap->status[startInd] = BLK_FIRST;
                  heap->status[startInd + currSize - 1] = BLK_LAST;
                  for(size_t j = startInd + 1; j < startInd + currSize - 1; j++){
                      heap->status[j] = BLK_CONT;
                  }
                  struct block_id result = block_id_new(size, heap);
                  result.ind = startInd;
                  return result;
              }
          } else {
              currSize = 0;
          }
      }
  }
  return block_id_invalid();
}
/* Free */

void block_free(struct block_id b) {
    b.heap->status[b.ind] = BLK_FREE;
    if(b.value != 1){
        for(size_t i = b.ind; i < b.ind + b.value; i++){
            b.heap->status[i] = BLK_FREE;
        }
    }
}

/* Printer */
const char* block_repr(struct block_id b) {
  static const char* const repr[] = {[BLK_FREE] = " .",
                                     [BLK_ONE] = " *",
                                     [BLK_FIRST] = "[=",
                                     [BLK_LAST] = "=]",
                                     [BLK_CONT] = " ="};
  if (b.valid)
    return repr[b.heap->status[b.value]];
  else
    return "INVALID";
}

void block_debug_info(struct block_id b, FILE* f) {
  fprintf(f, "%s", block_repr(b));
}

void block_foreach_printer(struct heap* h, size_t count,
                           void printer(struct block_id, FILE* f), FILE* f) {
  for (size_t c = 0; c < count; c++)
    printer(block_id_new(c, h), f);
}

void heap_debug_info(struct heap* h, FILE* f) {
  block_foreach_printer(h, HEAP_BLOCKS, block_debug_info, f);
  fprintf(f, "\n");
}
/*  -------- */

int main() {
  heap_debug_info(&global_heap, stdout);
    block_allocate(&global_heap,1);
    block_allocate(&global_heap,4);
    struct block_id bid = block_allocate(&global_heap,2);
    heap_debug_info(&global_heap, stdout);
    block_free(bid);
    heap_debug_info(&global_heap, stdout);
  return 0;
}
