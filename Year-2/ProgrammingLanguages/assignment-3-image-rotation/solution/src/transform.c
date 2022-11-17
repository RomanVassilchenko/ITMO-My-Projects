#include "../include/transform.h"

#include "../include/image.h"

struct image image_rotate_90deg_left(const struct image input) {
	struct image output = image_create(input.height, input.width);

	for (size_t input_row = 0; input_row < input.height; input_row++) {
		for (size_t input_column = 0; input_column < input.width; input_column++) {
			size_t output_row = input_column;
			size_t output_column = input.height - input_row -1;
			*image_pos_at(&output, output_column, output_row) = *image_pos_at(&input, input_column, input_row);
		}
	}
	return output;
}
