#ifndef TRANSFORM_H
#define TRANSFORM_H

#include "../include/image.h"

typedef struct image(image_transformer)(struct image input);

image_transformer image_rotate_90deg_left;

#endif
