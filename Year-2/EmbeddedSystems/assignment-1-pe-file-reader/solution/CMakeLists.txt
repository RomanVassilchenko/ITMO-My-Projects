file(GLOB_RECURSE sources CONFIGURE_DEPENDS
    src/*.c
    src/*.h
    include/*.h
)

add_executable(section-extractor ${sources} src/util.c src/include/util.h src/file_io.c src/include/file_io.h src/pe_util.c src/include/pe_util.h src/include/pe_file.h)
target_include_directories(section-extractor PRIVATE src include)
