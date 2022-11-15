//
// Created by rossilman on 15.11.22.
//

#ifndef VECTOR_H_
#define VECTOR_H_

#include <inttypes.h>
#include <malloc.h>
#include <stdio.h>
#include <stdbool.h>
#define MIN(x, y) (((x) < (y)) ? (x) : (y))

struct vector {
    int64_t *array;
    size_t capacity;
    size_t count;
};

struct maybe_int64_t {
    int64_t value;
    bool valid;
};

size_t vector_get_count(struct vector* array);
size_t vector_get_capacity(struct vector* array);

void vector_set_count(struct vector* array, size_t new_count);
void vector_set_capacity(struct vector* array, size_t new_capacity);
void vector_double_capacity(struct vector* array);

struct vector vector_create(size_t size);

struct maybe_int64_t vector_getter(struct vector* array, size_t index);
bool vector_setter(struct vector* array, size_t index, int64_t value);
bool vector_add_to_end(struct vector* array, int64_t value);
struct vector* vector_add_vector_to_end(struct vector* array1, struct vector* array2);

void vector_print_all(struct vector* array);

void vector_print_to_file_value(int64_t value, FILE* file);
void vector_print_to_file_array(struct vector* array, FILE* file);
void vector_print_capacity(struct vector* array);
void vector_print_count(struct vector* array);

void vector_free(struct vector* array);

#endif // VECTOR_H_