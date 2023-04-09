#include <stdio.h>

#include "bmp.h"
#include "file.h"
#include "image.h"
#include "transform.h"

const char *read_messages[] = {
        [READ_INVALID_HEADER] = "Invalid header! Couldn't read image header\0",
        [READ_INVALID_BITS] = "Invalid bits! Couldn't read image data\n"
};

const char *write_messages[] = {
        [WRITE_ERROR] = "Write error! Couldn't save transformed image"
};

int main(int argc, char **argv) {
    if (argc < 3) {
        fprintf(stderr, "Usage: %s <input-image-location> <output-image-location>\n", argv[0]);
    }

    char *input_image_location = argv[1];
    char *output_image_location = argv[2];

    struct image img;
    enum read_status read_status = read_image(input_image_location, from_bmp, &img) != READ_OK;
    if (read_status != READ_OK) {
        printf("%s", read_messages[read_status]);
        return 1;
    }

    struct image rotated_image = image_rotate_90deg_left(img);
    image_free(img);

    enum write_status write_status = write_image(output_image_location, to_bmp, rotated_image);
    if (write_status != WRITE_OK) {
        printf("%s", write_messages[write_status]);
        return 2;
    }
    image_free(rotated_image);

    return 0;
}
