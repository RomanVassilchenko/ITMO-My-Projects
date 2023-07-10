#ifndef IMAGE_H
#define IMAGE_H


#include <file.h>
#include <inttypes.h>

#define PIXEL_SIZE sizeof(struct pixel)

struct pixel {
	uint8_t b;
	uint8_t g;
	uint8_t r;
};


struct image {
	size_t width;
	size_t height;

	struct pixel *data;
};

struct image image_create(size_t width, size_t height);
struct pixel *image_pos_at(const struct image *img, size_t col, size_t row);
void image_free(const struct image img);


enum read_status {
    READ_OK = 0,
    READ_INVALID_BITS,
    READ_INVALID_HEADER
};

enum write_status {
    WRITE_OK = 0,
    WRITE_ERROR
};

typedef enum read_status (from_image)(FILE* in, struct image *const img);
typedef enum write_status (to_image)(FILE* out, const struct image img);

enum read_status read_image(const char* filename, from_image* from_image, struct image *img);
enum write_status write_image(const char* filename, to_image* to_image, struct image img);


#endif
