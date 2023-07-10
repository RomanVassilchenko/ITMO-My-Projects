/**

    @file pe_file.h
    @brief This file contains the definitions of the Portable Executable (PE) file format structures used by the section-extractor program.
    */

#ifndef SECTION_EXTRACTOR_PE_FILE_H
#define SECTION_EXTRACTOR_PE_FILE_H

#include <stdint.h>

/**

    @def MAIN_OFFSET
    @brief The offset in bytes to the starting address of the Portable Executable (PE) header.
    */
#define MAIN_OFFSET 0x3c
/**

    @def MAX_SECTION_NAME_LENGTH
    @brief The maximum length of a section name in bytes, including the null-terminating character.
    */
#define MAX_SECTION_NAME_LENGTH 8

/**

    @struct PEHeader
    @brief A structure representing the Portable Executable (PE) header of a PE file.
    */
struct __attribute__((packed)) PEHeader {
    uint16_t magicNumberOne;
    uint16_t magicNumberTwo;
    uint16_t Machine;
    uint16_t NumberOfSections;
    uint32_t TimeDateStamp;
    uint32_t PointerToSymbolTable;
    uint32_t NumberOfSymbols;
    uint16_t SizeOfOptionalHeader;
    uint16_t Characteristics;
};


/**

    @struct SectionHeader
    @brief A structure representing a section header in a PE file.
    */
struct __attribute__((packed)) SectionHeader {
    uint8_t Name[8];
    uint32_t VirtualSize;
    uint32_t VirtualAddres;
    uint32_t SizeOfRawData;
    uint32_t PointerToRawData;
    uint32_t PointerToRelocations;
    uint32_t PointerToLinenumbers;
    uint16_t NumberOfRelocations;
    uint16_t NumberOfLinenumbers;
    uint32_t Characteristics;
};

#endif //SECTION_EXTRACTOR_PE_FILE_H
