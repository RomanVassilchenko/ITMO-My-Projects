; print_string.asm 
section .data
; message: db  'hello, world!', 10

section .text
global _start

exit:
    mov  rax, 60
    xor  rdi, rdi          
    syscall



read_uint:

_start:

  call exit
