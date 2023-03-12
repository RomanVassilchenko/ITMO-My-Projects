#pragma once
#ifndef _IO_H_
#define _IO_H_

#define PRI_SPECIFIER(e) (_Generic( (e), uint16_t : "%" PRIu16, uint32_t: "%" PRIu32, default: "NOT IMPLEMENTED" ))

_Noreturn void fatal( const char* msg, ... );

#endif
