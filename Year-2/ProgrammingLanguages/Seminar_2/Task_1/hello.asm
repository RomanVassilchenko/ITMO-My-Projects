; hello.asm 
section .data
message: db  'hello, world!', 10

section .text
global _start

exit:                        ; Это метка начала функции exit
    mov  rax, 60             ; Это функция exit
    xor  rdi, rdi
    syscall

print_string:                ; Это метка начала функции print_string
    mov  rax, 1              ; Это функция print_string
    mov  rdi, 1
    mov  rsi, message
    mov  rdx, 14
    syscall
    ret                      ; Выход из функции print_string

_start:
    call print_string        ; Вызов функции print_string
    call print_string        ; Вызов функции print_string
    call exit                ; Вызов функции exit