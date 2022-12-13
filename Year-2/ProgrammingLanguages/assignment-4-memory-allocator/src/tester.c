#include "tester.h"

void* heap;
struct block_header* block;

static int assert_block_count(struct block_header* block) {
    int count = 0;
    struct block_header *iterator = block;
    while (iterator->next) {
        if (!iterator->is_free) count++;
        iterator = iterator->next;
    }
    return count;
}

static bool init_heap_for_tests() {
    heap = heap_init(REGION_MIN_SIZE);
    block = (struct block_header *) heap;
    if (!heap || !block)
        return false;
    return true;
}

static void test_1() {
    printf("[[Test 1]]\n");
    debug_heap(stdout, heap);
    printf("~~Malloc 1024~~\n");
    void *test = _malloc(1024);
    debug_heap(stdout, heap);
    if (!test || block->capacity.bytes != 1024) {
        printf("[Test 1 FAILED]\n");
    } else {
        printf("[Test 1 PASSED]\n");
    }
    _free(test);
}

static void test_2() {
    printf("[[Test 2]]\n");
    debug_heap(stdout, heap);
    printf("~~Malloc 1024~~\n");
    void *test_1 = _malloc(1024);
    printf("~~Malloc 2048~~\n");
    void *test_2 = _malloc(2048);
    debug_heap(stdout, heap);
    if (assert_block_count(block) != 2) {
        printf("[Test 2 FAILED]\n");
        _free(test_1);
        _free(test_2);
    } else {
        _free(test_1);
        debug_heap(stdout, heap);
        if (assert_block_count(block) == 1)
            printf("[Test 2 FAILED]\n");
        else
            printf("[Test 2 PASSED]\n");
        _free(test_2);
    }
}

static void test_3() {
    printf("[[Test 3]]\n");
    debug_heap(stdout, heap);
    printf("~~Malloc 1024~~\n");
    void *test_1 = _malloc(1024);
    printf("~~Malloc 2048~~\n");
    void *test_2 = _malloc(2048);
    printf("~~Malloc 4096~~\n");
    void *test_3 = _malloc(4096);
    debug_heap(stdout, heap);
    if (assert_block_count(block) != 3) {
        printf("[Test 3 FAILED]\n");
        _free(test_1);
        _free(test_2);
        _free(test_3);
    } else {
        _free(test_1);
        _free(test_2);
        debug_heap(stdout, heap);
        if (assert_block_count(block) == 1)
            printf("[Test 3 PASSED]\n");
        else
            printf("[Test 3 FAILED]\n");
        _free(test_3);
    }
}

static void test_4() {
    printf("[[Test 4]]\n");
    debug_heap(stdout, heap);
    printf("~~Malloc 8192~~\n");
    void *test_1 = _malloc(8192);
    printf("~~Malloc 4096~~\n");
    void *test_2 = _malloc(4096);
    debug_heap(stdout, heap);
    if (assert_block_count(block) != 2)
        printf("[Test 4 FAILED]\n");
    _free(test_1);
    _free(test_2);
    printf("[Test 4 PASSED]\n");
}

static void test_5() {
    printf("[[Test 5]]\n");
    printf("~~Malloc 2048~~\n");
    void *mem1 = _malloc(2048);
    printf("~~Malloc 2048~~\n");
    void *mem2 = _malloc(2048);
    debug_heap(stdout, heap);
    _free(mem1);
    _free(mem2);
    printf("[Test 5 PASSED]\n");
}



void run_tests() {
    if (init_heap_for_tests()) {
        test_1();
        test_2();
        test_3();
        test_4();
        test_5();
    } else printf("[Error in heap init!!!]\n");
}