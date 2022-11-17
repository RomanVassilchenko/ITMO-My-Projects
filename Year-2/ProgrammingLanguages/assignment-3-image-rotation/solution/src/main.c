#include <stdio.h>

#include "../include/bmp.h"
#include "../include/file.h"
#include "../include/image.h"
#include "../include/transform.h"

int main(int argc, char** argv) {
    if(argc < 3){
        fprintf(stderr,"Usage: %s <input-image-location> <output-image-location>\n", argv[0]);
    }

    char *input_image_location = argv[1];
    char *output_image_location = argv[2];

    FILE* in = reader_file_open(input_image_location);
    struct image img = (struct image) {0};

    switch (from_bmp(in, &img)) {
    case READ_INVALID_HEADER:
        fprintf(stderr, "Invalid header! Couldn't read image header from '%s'\n", input_image_location);
        return -1;
    case READ_INVALID_BITS:
        fprintf(stderr, "Invalid bits! Couldn't read image data from '%s'\n", input_image_location);
        return -1;
    case READ_OK:
        break;
    }

    struct image rotated_image = image_rotate_90deg_left(img);
    image_free(img);

    FILE* out = writer_file_open(output_image_location);

    switch (to_bmp(out, &rotated_image)) {
    case WRITE_ERROR:
        fprintf(stderr, "Write error! Couldn't save transformed image to '%s'\n", output_image_location);
        return -1;
    case WRITE_OK:
        break;
    }
    image_free(rotated_image);

    return 0;
}
