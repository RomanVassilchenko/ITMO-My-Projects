#include "../include/image.h"

#include <stdlib.h>

struct image image_create(size_t width, size_t height) {
    return (struct image) {
            .width = width,
            .height = height,
            .data = malloc(width * height * PIXEL_SIZE)
    };
}

struct pixel *image_pos_at(const struct image *const img, size_t col, size_t row) {
	return img->data + col + row * img->width;
}

void image_free(const struct image img) {
	free(img.data);
}

enum read_status read_image(const char* filename, from_image* from_image, struct image *img) {
    FILE* in = fopen(filename, "rb");

    enum read_status from_image_status = from_image(in, img);
    fclose(in);

    return from_image_status;
}



enum write_status write_image(const char* filename, to_image *to_image, struct image img) {
    FILE *out = fopen(filename, "wb");

    enum write_status to_image_status = to_image(out, img);
    fclose(out);

    return to_image_status;
}
