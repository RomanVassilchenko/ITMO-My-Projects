//
// Created by rossilman on 15.11.22.
//

#include "vector.h"

/*
 *
 * $ gcc -Wall -Wextra -std=c17 -c vector.c
 * $ gcc -Wall -Wextra -std=c17 -c main.c
 * $ gcc vector.o main.o
 * $ ./a.out
 *
 * Описание vector.h
 * struct vector arr1 = vector_create(5);
    struct vector* array = &(arr1);
    Описываем таким образом, чтобы всегда иметь доступ к объекту, а не его ссылке
 * */

int main() {
    // Создание нового вектора размера 5
    struct vector arr1 = vector_create(5);
    struct vector* array = &(arr1);

    // Вывод Count & Capacity
    vector_print_capacity(array);
    vector_print_count(array);

    // Добавление 15 элементов в вектор
    // Вектор несколько раз увеличит свой изначальный размер (2 раза)
    for(size_t i = 0; i < 15; i++){
        vector_setter(array, i, i * i);
    }

    // Вывод Count & Capacity
    vector_print_capacity(array);
    vector_print_count(array);

    // Добавление в конец вектора новый элемент (Пункт 3)
    vector_add_to_end(array,-5);

    // Вывод всех элементов массива
    vector_print_all(array);

    // Вывод Count & Capacity
    vector_print_capacity(array);
    vector_print_count(array);


    struct vector arr2 = vector_create(100);
    struct vector* array2 = &arr2;
    for(size_t i = 0; i < 10; i++){
        vector_setter(array2, i, i - i*i);
    }

    // Вывод всех элементов массива
    vector_print_all(array2);

    // Добавление элементов массива 2 к массиву 1
    vector_add_vector_to_end(array, array2);

    // Вывод всех элементов массива
    vector_print_all(array);

    // Вывод всех элементов массива
    vector_print_all(array2);

    // Вывод Count & Capacity
    vector_print_capacity(array2);
    vector_print_count(array2);

    // Изменение capacity
    vector_set_capacity(array2, 14);
    vector_print_all(array2);

    // Вывод Count & Capacity
    vector_print_capacity(array2);
    vector_print_count(array2);

    // Изменение capacity
    vector_set_capacity(array2, 3);
    vector_print_all(array2);

    // Вывод Count & Capacity
    vector_print_capacity(array2);
    vector_print_count(array2);

    // Запись в файл 123.txt
    FILE *fptr;
    fptr = fopen("123.txt","w");
    vector_print_to_file_array(array,fptr);

    //Очистка значений
    vector_free(array2);
    vector_free(array);
    return 0;
}