//
// Created by rossilman on 13.12.22.
//

#include <stdio.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/mman.h>
#include <pthread.h>
#include <semaphore.h>

void* create_shared_memory(size_t size) {
    return mmap(NULL,
                size,
                PROT_READ | PROT_WRITE,
                MAP_SHARED | MAP_ANONYMOUS,
                -1, 0);
}

int* shmem;
int N = 10;
pthread_t t1,t2;
sem_t sem_print, sem_end, sem_end_print;

void* parent_proc(void* args) {

    for(;;){
        sem_wait(&sem_print);

        if(sem_trywait(&sem_end) == 0) break;

        for(int i = 0; i < 10; i++){
            printf("%d ", shmem[i]);
        }
        printf("\n");
        sem_post(&sem_end_print);
    }

    return NULL;
}
void* child_proc(void* args) {
    for (;;) {
        int index, value;
        scanf("%d %d", &index, &value);
        if (index < 0 || index > N - 1) break;
        shmem[(size_t)index] = value;

        sem_post(&sem_print);
        sem_wait(&sem_end_print);
    }
    sem_post(&sem_end);
    sem_post(&sem_print);
    return NULL;
}

int task3() {
    shmem = create_shared_memory(sizeof (int) * N);

    for(size_t i = 1; i <= N; i++) shmem[i - 1] = i;

    sem_init(&sem_print,1,0);
    sem_init(&sem_end_print,1,0);
    sem_init(&sem_end,1,0);

    pthread_create(&t1, NULL, parent_proc, NULL);
    pthread_create(&t2, NULL, child_proc, NULL);

    pthread_join(t1, NULL);
    pthread_join(t2, NULL);

    sem_destroy(&sem_print);
    sem_destroy(&sem_end_print);
    sem_destroy(&sem_end);

    return 0;
}
