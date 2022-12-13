//
// Created by rossilman on 13.12.22.
//
/* cpu-reordering.c */

#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <inttypes.h>
#include <stdint.h>
#include <stdlib.h>
#include <time.h>
/*
 * В этом коде происходит та же логика, что и в примере ранее.
 Семафоры используются чтобы при каждом запуске итерации
 в thread0_impl/thread1_impl в момент sem_post потоки
 подождали друг друга.
 */
sem_t sem_begin0, sem_begin1, sem_end;

int x, y, read0, read1;

void *thread0_impl( void *param )
{
    for (;;) {
        sem_wait( &sem_begin0 );

        asm volatile("mfence"::: "memory");
        x = 1;
        asm volatile("mfence"::: "memory");
        read0 = y;

        sem_post( &sem_end );
    }
    return NULL;
}

void *thread1_impl( void *param )
{
    for (;;) {

        sem_wait( &sem_begin1 );

        asm volatile("mfence"::: "memory");
        y = 1;
        asm volatile("mfence"::: "memory");
        read1 = x;

        sem_post( &sem_end );
    }
    return NULL;
}

int task4( void ) {
    sem_init( &sem_begin0, 0, 0);
    sem_init( &sem_begin1, 0, 0);
    sem_init( &sem_end, 0, 0);

    pthread_t thread0, thread1;
    pthread_create( &thread0, NULL, thread0_impl, NULL);
    pthread_create( &thread1, NULL, thread1_impl, NULL);

    for (uint64_t i = 0; i < 1000000; i++)
    {
        asm volatile("mfence"::: "memory");
        x = 0;
        y = 0;
        sem_post( &sem_begin0 );
        sem_post( &sem_begin1 );

        sem_wait( &sem_end );
        sem_wait( &sem_end );

        if (read0 == 0 && read1 == 0 ) {
            printf( "reordering happened on iteration %" PRIu64 "\n", i );
            exit(0);
        }
    }
    puts("No reordering detected during 1000000 iterations");
    return 0;
}
