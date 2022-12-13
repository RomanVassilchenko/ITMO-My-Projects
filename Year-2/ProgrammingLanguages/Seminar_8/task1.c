//
// Created by rossilman on 13.12.22.
//

/* fork-example-1.c */

#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>

void* create_shared_memory(size_t size) {
    return mmap(NULL,
                size,
                PROT_READ | PROT_WRITE,
                MAP_SHARED | MAP_ANONYMOUS,
                -1, 0);
}


int task1() {
    int* shmem = create_shared_memory(sizeof (int) * 10);

    for(size_t i = 0; i < 10; i++){
        shmem[i] = i + 1;
    }

    printf("Shared memory at: %p\n" , shmem);
    int pid = fork();

    if(pid == 0){
        size_t i;
        int value;
        scanf("%zu %d", &i, &value);
        if(i >= 1 && i <= 10) shmem[i - 1] = value;
    } else {
        wait(NULL);

        for(int i = 0; i < 10; i++){
            printf("%d ", shmem[i]);
        }
    }
}