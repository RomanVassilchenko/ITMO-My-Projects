%include "defines.asm"

; Constants for application
section .rodata:
	WELCOME: db "assignment-2-dictionary | Vassilchenko Roman | P32081", 0
	PROMNT: db "Key > ", 0
	VALUE_NOT_FOUND: db "Sorry, but there is no such key in dictionary!", 0
	VALUE_FOUND: db "The Value: ", 0
	INPUT_IS_TOO_BIG: db "Error: input is too long", 10, 0
	CURRENT_BUFFER_SIZE: db "Current buffer size is equal to ", 0