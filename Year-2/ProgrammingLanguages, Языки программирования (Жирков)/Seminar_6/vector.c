#include "vector.h"


size_t vector_get_count(struct vector* array) {
    return array->count;
}
size_t vector_get_capacity(struct vector* array) {
    return array->capacity;
}

void vector_set_count(struct vector* array, size_t new_count){
    array->count = new_count;
}
void vector_set_capacity(struct vector* array, size_t new_capacity){
    array->array = realloc(array->array, sizeof(int64_t) * new_capacity);
    array->capacity = new_capacity;
    vector_set_count(array, MIN(vector_get_count(array), new_capacity));
}
void vector_double_capacity(struct vector* array){
    vector_set_capacity(array, vector_get_capacity(array) * 2);
}

struct vector vector_create(size_t size){
    int64_t *array = malloc(sizeof(int64_t) * size);
    struct vector result;
    result.array = array;
    result.count = 0;
    result.capacity = size;
    return result;
}

struct maybe_int64_t vector_getter(struct vector* array, size_t index){
    struct maybe_int64_t result;
    if(index >= vector_get_capacity(array)) {
        result.valid = false;
    } else {
        result.valid = true;
        result.value = array->array[index];
    }
    return result;
}
bool vector_setter(struct vector* array, size_t index, int64_t value){
    if(vector_get_count(array) + 1 >= vector_get_capacity(array)){
        vector_double_capacity(array);
    }
    if(index >= vector_get_capacity(array)) return false;
    if(index > vector_get_count(array)) vector_set_count(array,index + 1);
    array->array[index] = value;
    return true;
}
bool vector_add_to_end(struct vector* array, int64_t value){
    bool result = vector_setter(array, vector_get_count(array), value);
    if(result) vector_set_count(array,vector_get_count(array) + 1);
    return true;
}
struct vector* vector_add_vector_to_end(struct vector* array1, struct vector* array2){
    for(size_t i = 0; i < vector_get_count(array2); i++){
        vector_add_to_end(array1, vector_getter(array2, i).value);
    }
    return array1;
}

void vector_print_all(struct vector* array){
    for(size_t i = 0; i < vector_get_count(array); i++){
        printf("%" PRId64 " ", vector_getter(array, i).value);
    }
    printf("\n");
}

void vector_print_to_file_value(int64_t value, FILE* file){
    if(file == NULL) return;
    fprintf(file, "% " PRId64, value);
}
void vector_print_to_file_array(struct vector* array, FILE* file){
    if(file == NULL) return;
    for(size_t i = 0; i < vector_get_count(array); i++){
        vector_print_to_file_value(vector_getter(array,i).value, file);
    }
}
void vector_print_capacity(struct vector* array){
    printf("Capacity = %zu \n", vector_get_capacity(array));
}
void vector_print_count(struct vector* array){
    printf("Count = %zu \n", vector_get_count(array) + 1);
}

void vector_free(struct vector* array){
    free(array->array);
}