section .data
; message: db  'hello, world!', 10

section .text
global _start

exit:
    mov  rax, 60
    xor  rdi, rdi          
    syscall

parse_uint:
    mov rsi, 0x0A
    xor r8, r8
    xor rax, rax
    xor rcx, rcx
.loop:
    mov al, byte[rdi + rcx]
    cmp al, '0'
    jb .finally
    cmp al, '9'
    ja .finally
    sub al, '0'
    push rax
    mov rax, r8
    mul rsi
    mov r8, rax
    pop rax

    add r8, rax
    inc rcx
    jmp .loop

.finally:
    mov rdx, rcx
    mov rax, r8  
    ret