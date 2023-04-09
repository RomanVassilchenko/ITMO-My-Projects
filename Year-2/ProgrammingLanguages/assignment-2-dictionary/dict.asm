section .text
global find_word

%include "lib.inc"
%include "defines.asm"

find_word:
    .loop:
        cmp rsi, 0          ; Check if it is the most extreme element 
        je .not_found
        
        push rdi            ; Backup values
        push rsi            ; 
        
        add rsi, PTR_SIZE   ; Compare keys
        call string_equals  ;
        
        pop rsi             ; Restore values
        pop rdi             ; 
        
        cmp rax, 1          ; string_equals -> rax
        je .found           ; (1, 0) - (found, not found)

        mov rsi, [rsi]      ; Shift to the next element
        jmp .loop           ;

    .found:
        add rsi, PTR_SIZE   ;
        mov rax, rsi        ; Found -> rax = pointer to the value
        ret                 ;

    .not_found:             ;
        mov rax, 0          ; Not found -> rax = 0
        ret                 ;

