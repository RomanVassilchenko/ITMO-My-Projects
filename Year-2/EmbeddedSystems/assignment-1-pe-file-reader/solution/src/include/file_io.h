/**

    @file file_io.h
    @brief Functions for file input and output operations
    */

#ifndef SECTION_EXTRACTOR_FILE_IO_H
#define SECTION_EXTRACTOR_FILE_IO_H

#include <stdint.h>
#include <stdio.h>

#define EXIT_SUCCESS 0
#define EXIT_FAILURE 1

/**

    @brief Prints the program usage to a given output stream
    @param stream The output stream to print to
    @param program_name The name of the program to include in the usage message
    */
void print_usage(FILE* stream, const char* program_name);
/**

    @brief Opens a file with a given filename and mode
    @param filename The name of the file to open
    @param mode The mode in which to open the file
    @return Returns a pointer to the opened file
    @note Exits with failure if opening the file fails
    */
FILE* open_file(const char* filename, const char* mode);
/**

    @brief Reads a specified amount of data from a file into a buffer
    @param buffer The buffer to read data into
    @param size The size of each element to read
    @param count The number of elements to read
    @param file The file to read data from
    @return Returns the number of elements successfully read
    @note Exits with failure if reading from the file fails
    */
size_t read_file(void* buffer, size_t size, size_t count, FILE* file);
/**

    @brief Writes a specified amount of data from a buffer into a file
    @param buffer The buffer to write data from
    @param size The size of each element to write
    @param count The number of elements to write
    @param file The file to write data to
    @return Returns the number of elements successfully written
    @note Exits with failure if writing to the file fails
    */
size_t write_file(const void* buffer, size_t size, size_t count, FILE* file);
/**

    @brief Closes a file and checks if it was closed successfully.
    This function takes a pointer to a file and closes it. It checks if the file was closed successfully, and if not, it prints an error message to standard error and exits the program with a failure status code.
    @param file Pointer to the file to be closed.
    */
void close_file(FILE* file);

/**

    @brief Moves the file pointer to a specific position within the file.
    @param source The input file pointer.
    @param offset The number of bytes to move the file pointer.
    @param seek The starting position from which to move the file pointer.
    @return Returns 0 if successful, and an error code otherwise.
    */
size_t fseek_file(FILE* source, uint16_t offset, uint8_t seek);

#endif //SECTION_EXTRACTOR_FILE_IO_H
