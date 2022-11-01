#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <inttypes.h>


void error(const char *s) {
    fprintf(stderr, "%s", s);
    abort();
}

#define _print(type, x) type##_print(x)

#define print(x)                                                        \
  _Generic((x),                                                         \
           int64_t : int64_t_print(x),                                  \
           double : double_print(x),                                    \
           default : error("Unsupported operation"))

void int64_t_print(int64_t i) { printf("% " PRId64, i); }
void double_print(double d) { printf("%lf ", d); }
void newline_print() { puts("\n"); }

#define DEFINE_LIST(type)                                               \
  typedef struct list_##type list_##type;                               \
  struct list_##type {                                                  \
    type value;                                                         \
    struct list_##type* next;                                           \
  };

DEFINE_LIST(int64_t)
DEFINE_LIST(double)

#define list_create(x) \
    _Generic((x),      \
        int64_t: list_int64_t_create(x), \
        double: list_double_create(x),                  \
        default: error("Invalid type create")                       \
    )

#define list_push(x,y) \
    _Generic((x),      \
        list_int64_t**: list_int64_t_push(x,y), \
        list_double**: list_double_push(x,y),                  \
        default: error("Invalid type push")                       \
    )

#define list_print(x) \
    _Generic((x),      \
        list_int64_t* : list_int64_t_print(x), \
        list_double* : list_double_print(x),                  \
        default: error("Invalid type print")                       \
    )



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
    list_int64_t *int_head = list_create((int64_t) 1);
    list_push(&int_head, 2);
    list_push(&int_head, 3);
    list_push(&int_head, 4);
    list_push(&int_head, 5);
    list_print(int_head);
    newline_print();

    list_int64_t *int_head_2 = list_create((int64_t) 10);
    list_push(&int_head_2, 9);
    list_push(&int_head_2, 8);
    list_print(int_head_2);
    newline_print();

    list_double *double_head = list_create((double) 7);
    list_push(&double_head, 9);
    list_push(&double_head, 8);
    list_print(double_head);
    newline_print();
    return 0;
}