#ifndef SECTION_EXTRACTOR_PE_UTIL_H
#define SECTION_EXTRACTOR_PE_UTIL_H

#include "pe_file.h"
#include <bits/types/FILE.h>
#include <stddef.h>

/**

    @brief Seek to the Portable Executable header in the input file
    @param stream The input file stream
    @return Returns 0 if successful and 1 if an error occurred
    */
int seek_pe_header(FILE* stream);
/**

    @brief Read the Portable Executable header from the input file
    @param stream The input file stream
    @param header A pointer to a struct to store the header data
    @return Returns 0 if successful
    */
int read_pe_header(FILE* stream, struct PEHeader* header);
/**

    @brief Read the section header from the input file
    @param stream The input file stream
    @param section A pointer to a struct to store the section header data
    @return Returns 0 if successful
    */
int read_section_header(FILE* stream, struct SectionHeader* section);
/**

    @brief Get the length of a section name
    @param name A pointer to the section name
    @return The length of the section name
    */
size_t section_name_length(const uint8_t* name);
/**

    @brief Compare two section names
    @param name_1 The first section name to compare
    @param name_2 The second section name to compare
    @return Returns 0 if the section names are the same, otherwise returns 1
    */
int compare_section_names(char* name_1, uint8_t* name_2);
/**

    @brief Copy a section of a file from the source file to the destination file
    @param source The input file stream
    @param destination The output file stream
    @param amount The number of bytes to copy
    @return Returns 0 if successful and 1 if an error occurred
    */
int copy_file_section(FILE* source, FILE* destination, size_t amount);

/**

    @brief Copy a section from a Portable Executable (PE) file to another file
    @param source The input file stream
    @param section_name The name of the section to copy
    @param destination The output file stream
    @return Returns 0 if successful and 1 if an error occurred
    */
int copy_pe_section(FILE* source, char* section_name, FILE* destination);

#endif //SECTION_EXTRACTOR_PE_UTIL_H
