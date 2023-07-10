
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <inttypes.h>

#define _print(type, x) type##_print(x)

void int64_t_print(int64_t i) { printf("%" PRId64, i); }
void double_print(double d) { printf("%lf ", d); }
void newline_print() { puts(""); }

#define DEFINE_LIST(type)                                               \
  typedef struct list_##type list_##type;                               \
  struct list_##type {                                                  \
    type value;                                                         \
    struct list_##type* next;                                           \
  };                                                                    \


#define push(type, head, value) list_##type##_push(head, value);
#define print_list(type, head) list_##type##_print(head);

DEFINE_LIST(int64_t)
DEFINE_LIST(double)

list_double* list_double_create(double x) {
    struct list_double* node = malloc(sizeof(struct list_double));
    node->value = x;
    return node;
}

list_int64_t* list_int64_t_create(int64_t x) {
    struct list_int64_t * node = malloc(sizeof(struct list_int64_t));
    node->value = x;
    return node;
}


void list_int64_t_push(list_int64_t **head, int64_t value) {
    list_int64_t *newHead = (list_int64_t*) malloc(sizeof(list_int64_t));
    newHead->value = value;
    newHead->next = (*head);
    (*head) = newHead;
}

void list_double_push(list_double **head, double value) {
    list_double *newHead = (list_double*) malloc(sizeof(list_double));
    newHead->value = value;
    newHead->next = (*head);
    (*head) = newHead;
}

void list_int64_t_print(list_int64_t *head) {
    while(head!=0) {
        _print(int64_t, head->value);
        head = head->next;
    }
}

void list_double_print(list_double *head) {
    while(head!=0) {
        _print(double , head->value);
        head = head->next;
    }
}


int main() {
    list_int64_t *int_head = list_int64_t_create(1);
    push(int64_t, &int_head, 2);
    push(int64_t, &int_head, 3);
    push(int64_t, &int_head, 4);
    print_list(int64_t, int_head);
    newline_print();

    list_double *double_head = list_double_create(9);
    push(double , &double_head, 8);
    push(double , &double_head, 7);
    print_list(double , double_head);
    newline_print();

    list_int64_t *int_head_2 = list_int64_t_create(19);
    push(int64_t, &int_head_2, 1);
    print_list(int64_t, int_head_2);
    newline_print();
    return 0;
}