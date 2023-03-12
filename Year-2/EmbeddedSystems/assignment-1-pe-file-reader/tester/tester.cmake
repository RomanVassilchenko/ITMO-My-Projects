# CMP0007: list command no longer ignores empty elements.
if(POLICY CMP0007)
    cmake_policy(SET CMP0007 NEW)
endif()

function(exec_check)
    execute_process(COMMAND ${ARGV}
        OUTPUT_VARIABLE out
        ERROR_VARIABLE  err
        RESULT_VARIABLE result)
    if(result)
        string(REPLACE "/" ";" name_components ${ARGV0})
        list(GET name_components -1 name)
        if(NOT out)
            set(out "<empty>")
        endif()
        if(NOT err)
            set(err "<empty>")
        endif()
        message(FATAL_ERROR "\nError running \"${name}\"\n*** Output: ***\n${out}\n*** Error: ***\n${err}\n")
    endif()
endfunction()

file(STRINGS ${TEST_DIR}/section SECTION_NAME)
file(REMOVE ${TEST_DIR}/output.bin)
exec_check(${SECTION_EXTRACTOR} ${TEST_DIR}/input.exe ${SECTION_NAME} ${TEST_DIR}/output.bin)
exec_check(${FILE_MATCHER} ${TEST_DIR}/output.bin ${TEST_DIR}/output_expected.bin)
