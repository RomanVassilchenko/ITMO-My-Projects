; main.asm
section .text
extern print_hex
global _start
_start:
    mov  rdi, 0x1122334455667788
    call print_hex
    call exit

exit:
    mov  rax, 60
    xor  rdi, rdi
    syscall