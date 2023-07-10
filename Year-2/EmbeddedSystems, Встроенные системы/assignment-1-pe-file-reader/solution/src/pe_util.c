#include "include/pe_util.h"
#include "include/file_io.h"
#include "include/util.h"
#include <stdio.h>

/**

    @brief Seek to the Portable Executable header in the input file
    @param stream The input file stream
    @return Returns 0 if successful and 1 if an error occurred
    */
int seek_pe_header(FILE* stream){
    fseek_file(stream, MAIN_OFFSET, SEEK_SET);

    uint32_t offset;
    read_file(&offset, 4, 1, stream);


    fseek_file(stream, offset, SEEK_SET);

    return 0;
}
/**

    @brief Read the Portable Executable header from the input file
    @param stream The input file stream
    @param header A pointer to a struct to store the header data
    @return Returns 0 if successful
    */
int read_pe_header(FILE* stream, struct PEHeader* header){
    read_file(header, sizeof(struct PEHeader), 1, stream);
    return 0;
}
/**

    @brief Read the section header from the input file
    @param stream The input file stream
    @param section A pointer to a struct to store the section header data
    @return Returns 0 if successful
    */
int read_section_header(FILE* stream, struct SectionHeader* section){
    read_file(section, sizeof(struct SectionHeader), 1, stream);
    return 0;
}
/**

    @brief Get the length of a section name
    @param name A pointer to the section name
    @return The length of the section name
    */
size_t section_name_length(const uint8_t* name){
    for(size_t len = 0; len < MAX_SECTION_NAME_LENGTH; len++){
        if(name[len] == '\0'){
            return len;
        }
    }

    return MAX_SECTION_NAME_LENGTH;
}
/**

    @brief Compare two section names
    @param name_1 The first section name to compare
    @param name_2 The second section name to compare
    @return Returns 0 if the section names are the same, otherwise returns 1
    */
int compare_section_names(char* name_1, uint8_t* name_2){
    size_t len_1 = str_length(name_1);
    size_t len_2 = section_name_length(name_2);

    if(len_1 != len_2){
        return 1;
    }

    if(mem_cmp(name_1, name_2, len_1) != 0){
        return 1;
    }

    return 0;
}
/**

    @brief Copy a section of a file from the source file to the destination file
    @param source The input file stream
    @param destination The output file stream
    @param amount The number of bytes to copy
    @return Returns 0 if successful and 1 if an error occurred
    */
int copy_file_section(FILE* source, FILE* destination, size_t amount){
    uint8_t buffer;
    size_t st;
    for (size_t i = 0; i < amount; i += sizeof(buffer)){
        st = read_file(&buffer, sizeof(buffer), 1, source);
        if(st){
            write_file(&buffer, sizeof(buffer), 1, destination);
        }else{
            return 1;
        }
    }

    return 0;
}
/**

    @brief Copy a section from a Portable Executable (PE) file to another file
    @param source The input file stream
    @param section_name The name of the section to copy
    @param destination The output file stream
    @return Returns 0 if successful and 1 if an error occurred
    */
int copy_pe_section(FILE* source, char* section_name, FILE* destination){

    struct PEHeader header;

    seek_pe_header(source);
    read_pe_header(source, &header);

    fseek_file(source, header.SizeOfOptionalHeader, SEEK_CUR);

    struct SectionHeader section;
    size_t i;
    for(i = 0; i < header.NumberOfSections; i++){
        read_section_header(source, &section);

        if(compare_section_names(section_name, section.Name) == 0){
            break;
        }
    }
    if(i == header.NumberOfSections){
        return 1;
    }

    fseek_file(source, section.PointerToRawData, SEEK_SET);

    int result = copy_file_section(source, destination, section.SizeOfRawData);
    if(result){
        return 1;
    }

    return 0;
}
