/**
@file main.c
@brief This file contains the main function of the program which extracts a section from a Portable Executable (PE) file
@details The function reads in the name of an input PE file, a section name to extract, and the name of the output file.
It then extracts the specified section from the input file and writes it to the output file.
*/

#include "include/file_io.h"
#include "include/pe_util.h"
#include <stdlib.h>

/**
@brief Checks if the number of arguments passed to the program is correct
@param argc The number of command-line arguments.
@param argv An array of command-line arguments.
*/
void check_args(int argc, char** argv){
    if (argc != 4) {
        print_usage(stderr, argv[0]);
        exit(EXIT_FAILURE);
    }
}

/**
@brief The main function of the program
@details This function reads in the name of an input PE file, a section name to extract, and the name of the output file.
It then extracts the specified section from the input file and writes it to the output file.
@param argc The number of command-line arguments.
@param argv An array of command-line arguments.
@return Returns 0 if successful, and 1 otherwise.
*/
int main(int argc, char** argv) {

    check_args(argc, argv);

    FILE* input_file = open_file(argv[1], "rb");
    FILE* output_file = open_file(argv[3], "wb");

    int status = copy_pe_section(input_file, argv[2], output_file);

    close_file(input_file);
    close_file(output_file);

    if (status != 0) {
        fprintf(stderr, "Error: failed to copy section\n");
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS;
}
