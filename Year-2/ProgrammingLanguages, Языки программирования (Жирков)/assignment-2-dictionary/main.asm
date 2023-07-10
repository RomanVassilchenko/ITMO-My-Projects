%include "words.inc"
%include "constants.asm"
%include "lib.inc"


global _start
extern find_word

section .bss
    buffer: resb BUFFER_SIZE

section .text
_start:
    mov rdi, WELCOME                ;
    mov rsi, STD_OUT                ; Welcome text
    call print_string               ;
    call print_newline              ;

    mov rdi, CURRENT_BUFFER_SIZE    ; 
    mov rsi, STD_OUT                ; 
    call print_string               ; Print current buffer size
    mov rdi, BUFFER_SIZE            ; 
    call print_uint                 ; 
    call print_newline              ; 

    .print_promnt:
        mov rdi, PROMNT             ; Print promnt
        call print_string           ;
        
    mov rcx, 0    

    .loop:
        push rcx
        call read_char              ; Read next symbol
        pop rcx

        cmp al, 0xA                 ; 
        je .success                 ; If end of line, then start processing
        cmp al, 0                   ; 
        je .success                 ;

        mov byte[buffer + rcx], al  ; Send symbol to buffer 
        inc rcx                     ;
        cmp rcx, BUFFER_SIZE        ; If size > Buffer_SIZE -> .fail
        je .fail
        jmp .loop

    .success:
        mov rdi, buffer             ;
        mov rsi, start_element      ; Send data from buffer and call find_word
        call find_word              ;

        cmp rax, 0                  ; find_word -> rax
        je .not_found               ; if rax == 0 -> .not_found
        
        mov rdi, rax                ; find_word -> rax
        push rdi                    ;
        call string_length          ; Get length of value
        pop rdi                     ; Restore pointer to the start of the element
        add rdi, rax                ; Move to the value (rdi + length of key)
        inc rdi                     ; rdi = rdi + 1 (Because of 0-terminator symbol of key)
        call print_string
        call print_newline
        call exit

    .fail:                      
        mov rdi, INPUT_IS_TOO_BIG   ; 
        mov rsi, STD_ERR            ; 
        call print_string           ; If error with buffersize ==> Print buffer size error message
        call print_newline          ; 
        call exit                   ; 

    .not_found:
        mov rdi, VALUE_NOT_FOUND    ;
        mov rsi, STD_OUT            ;
        call print_string           ; If key is not found ==> Print key is not found message
        call print_newline          ;
        call exit                   ;