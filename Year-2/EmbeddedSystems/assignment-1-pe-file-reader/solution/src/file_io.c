/**

    @file file_io.c
    @brief Functions for file input and output operations
    */
#include "include/file_io.h"
#include <stdlib.h>


/**

    @brief Prints the program usage to a given output stream
    @param stream The output stream to print to
    @param program_name The name of the program to include in the usage message
    */
void print_usage(FILE* stream, const char* program_name) {
    fprintf(stream, "Usage: %s <input_file> <section_name> <output_file>\n", program_name);
}

/**

    @brief Opens a file with a given filename and mode
    @param filename The name of the file to open
    @param mode The mode in which to open the file
    @return Returns a pointer to the opened file
    @note Exits with failure if opening the file fails
    */
FILE* open_file(const char* filename, const char* mode) {
    FILE* file = fopen(filename, mode);
    if (file == NULL) {
        fprintf(stderr, "Error: could not open file '%s' in mode '%s'\n", filename, mode);
        exit(EXIT_FAILURE);
    }
    return file;
}

/**

    @brief Reads a specified amount of data from a file into a buffer
    @param buffer The buffer to read data into
    @param size The size of each element to read
    @param count The number of elements to read
    @param file The file to read data from
    @return Returns the number of elements successfully read
    @note Exits with failure if reading from the file fails
    */
size_t read_file(void* buffer, size_t size, size_t count, FILE* file) {
    size_t result = fread(buffer, size, count, file);
    if (result != count) {
        fprintf(stderr, "Error: failed to read from file\n");
        exit(EXIT_FAILURE);
    }
    return result;
}

/**

    @brief Writes a specified amount of data from a buffer into a file
    @param buffer The buffer to write data from
    @param size The size of each element to write
    @param count The number of elements to write
    @param file The file to write data to
    @return Returns the number of elements successfully written
    @note Exits with failure if writing to the file fails
    */
size_t write_file(const void* buffer, size_t size, size_t count, FILE* file) {
    size_t result = fwrite(buffer, size, count, file);
    if (result != count) {
        fprintf(stderr, "Error: failed to write to file\n");
        exit(EXIT_FAILURE);
    }
    return result;
}

/**

    @brief Closes a file and checks if it was closed successfully.
    This function takes a pointer to a file and closes it. It checks if the file was closed successfully, and if not, it prints an error message to standard error and exits the program with a failure status code.
    @param file Pointer to the file to be closed.
    */

void close_file(FILE* file) {
    if (fclose(file) != 0) {
        fprintf(stderr, "Error: failed to close file\n");
        exit(EXIT_FAILURE);
    }
}
/**

    @brief Moves the file pointer to a specific position within the file.
    @param source The input file pointer.
    @param offset The number of bytes to move the file pointer.
    @param seek The starting position from which to move the file pointer.
    @return Returns 0 if successful, and an error code otherwise.
    */
size_t fseek_file(FILE* source, uint16_t offset, uint8_t seek){
    size_t result = fseek(source, offset, seek);
    if (result) {
        fprintf(stderr, "Error: failed to fseek file\n");
        exit(EXIT_FAILURE);
    }
    return result;
}
