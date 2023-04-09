#pragma once
#ifndef _COMMON_H_
#define _COMMON_H_

#define EXECUTABLE_NAME "file_matcher"

#ifdef __STDC_VERSION__ 
  #if __STDC_VERSION__ >= 201710L
    #define _COMMON_C17
  #endif
  #if __STDC_VERSION__ >= 201112L
    #define _COMMON_C11
  #endif
#endif

#endif
