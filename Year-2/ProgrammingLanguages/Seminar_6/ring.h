/* ring.h */

#pragma once
#ifndef _LLP_RING_H_
#define _LLP_RING_H

#include <stdlib.h>


#define DECLARE_RING(name, type)                                      \
struct ring_##name {                                                  \
  type value;                                                         \
  struct ring_##name* next;                                           \
  struct ring_##name* prev;                                           \
};

#define DEFINE_RING(name, type)                                       \
static struct ring_##name *ring_##name##_create(type value)           \
{                                                                     \
  struct ring_##name *item = malloc(sizeof(struct ring_##name));      \
  if (item == NULL)                                                   \
    return NULL;                                                      \
  item->value = value;                                                \
  item->next = item;                                                  \
  item->prev = item;                                                  \
  return item;                                                        \
}                                                                     \
static type ring_##name##_last(struct ring_##name *ring)              \
{                                                                     \
  if (ring == NULL)                                                   \
    return *(type *)NULL;                                             \
  return ring->prev->value;                                           \
}                                                                     \
static type ring_##name##_first(struct ring_##name *ring)             \
{                                                                     \
  if (ring == NULL)                                                   \
    return *(type *)NULL;                                             \
  return ring->value;                                                 \
}                                                                     \
static struct ring_##name *ring_##name##_push(                        \
    struct ring_##name **ring, type value)                            \
{                                                                     \
  if (ring == NULL)                                                   \
    return NULL;                                                      \
  struct ring_##name *item = ring_##name##_create(value);             \
  if (item == NULL)                                                   \
    return NULL;                                                      \
  if (*ring == NULL)                                                  \
  {                                                                   \
    *ring = item;                                                     \
    return item;                                                      \
  }                                                                   \
  item->next = *ring;                                                 \
  item->prev = (*ring)->prev;                                         \
  (*ring)->prev->next = item;                                         \
  (*ring)->prev = item;                                               \
  return item;                                                        \
}                                                                     \
static type ring_##name##_pop(struct ring_##name **ring)              \
{                                                                     \
  if (ring == NULL)                                                   \
    return *(type *)NULL;                                             \
  struct ring_##name *item = (*ring)->prev;                           \
  type ret = item->value;                                             \
  if (item == *ring)                                                  \
  {                                                                   \
    *ring = NULL;                                                     \
    free(item);                                                       \
    return ret;                                                       \
  }                                                                   \
  item->prev->next = *ring;                                           \
  (*ring)->prev = item->prev;                                         \
  free(item);                                                         \
  return ret;                                                         \
}                                                                     \
static struct ring_##name *ring_##name##_push_top(                    \
    struct ring_##name **ring, type value)                            \
{                                                                     \
  struct ring_##name *item = ring_##name##_push(ring, value);         \
  if (item == NULL)                                                   \
    return NULL;                                                      \
  *ring = item;                                                       \
  return item;                                                        \
}                                                                     \
static type ring_##name##_pop_top(struct ring_##name **ring)          \
{                                                                     \
  if (ring == NULL)                                                   \
    return *(type *)NULL;                                             \
  *ring = (*ring)->next;                                              \
  return ring_##name##_pop(ring);                                     \
}                                                                     \
static void ring_##name##_free(struct ring_##name **ring)             \
{                                                                     \
  if (*ring == NULL)                                                  \
    return;                                                           \
  struct ring_##name *tmp;                                            \
  (*ring)->prev->next = NULL;                                         \
  while (*ring != NULL)                                               \
  {                                                                   \
    tmp = *ring;                                                      \
    *ring = (*ring)->next;                                            \
    free(tmp);                                                        \
  }                                                                   \
}

#define DEFINE_RING_PRINT(name, printer)                              \
static void ring_##name##_print(struct ring_##name *ring)             \
{                                                                     \
  printf("-> ");                                                      \
  if (ring == NULL)                                                   \
  {                                                                   \
    printf("NULL -> \n");                                             \
    return;                                                           \
  }                                                                   \
  printer(ring->value);                                               \
  struct ring_##name *next = ring->next;                              \
  while (next != ring)                                                \
  {                                                                   \
    printf(" -> ");                                                   \
    printer(next->value);                                             \
    next = next->next;                                                \
  }                                                                   \
  printf(" ->\n");                                                    \
}                                                                     \
static void ring_##name##_print_back(struct ring_##name *ring)        \
{                                                                     \
  printf("<- ");                                                      \
  if (ring == NULL)                                                   \
  {                                                                   \
    printf("NULL <- \n");                                             \
    return;                                                           \
  }                                                                   \
  printer(ring->value);                                               \
  struct ring_##name *prev = ring->prev;                              \
  while (prev != ring)                                                \
  {                                                                   \
    printf(" <- ");                                                   \
    printer(prev->value);                                             \
    prev = prev->prev;                                                \
  }                                                                   \
  printf(" <-\n");                                                    \
}

#endif
