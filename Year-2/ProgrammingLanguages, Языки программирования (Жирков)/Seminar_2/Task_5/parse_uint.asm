section .data
        input: db '1', 0
        section .text
        %include "lib.inc"
        print_uint_test:
        mov rax, rdi
        push r12
        push r13
        push r15
        xor r12, r12
        mov r13, 32
        mov r15, 10
        push r12
        push r12
        push r12
        push r12
        ._A:
        test rax, rax
        jz ._B
        xor rdx, rdx
        div r15
        add dl, '0'
        dec r13
        mov byte [rsp + r13], dl
        inc r12
        jmp ._A
        ._B:
        test r12, r12
        jnz ._C
        dec r13
        mov byte [rsp + r13], '0'
        inc r12
        ._C:
        mov rax, 1
        mov rdi, 1
        lea rsi, [rsp + r13]
        mov rdx, r12
        syscall
        add rsp, 32
        pop r15
        pop r13
        pop r12
        ret
        global _start 
        _start:
        
mov rdi, -1
mov rsi, -1
mov rax, -1
mov rcx, -1
mov rdx, -1
mov r8, -1
mov r9, -1
mov r10, -1
mov r11, -1
push rbx
push rbp
push r12 
push r13 
push r14 
push r15 

        mov rdi, input
        call parse_uint
        
cmp r15, [rsp] 
jne .convention_error
pop r15
cmp r14, [rsp] 
jne .convention_error
pop r14
cmp r13, [rsp] 
jne .convention_error
pop r13
cmp r12, [rsp] 
jne .convention_error
pop r12
cmp rbp, [rsp] 
jne .convention_error
pop rbp
cmp rbx, [rsp] 
jne .convention_error
pop rbx

jmp continue

.convention_error:
    mov rax, 1
    mov rdi, 2
    mov rsi, err_calling_convention
    mov rdx,  err_calling_convention.end - err_calling_convention
    syscall
    mov rax, 60
    mov rdi, -41
    syscall
section .data
err_calling_convention: db "You did not respect the calling convention! Check that you handled caller-saved and callee-saved registers correctly", 10
.end:
section .text
continue:

        push rdx
        mov rdi, rax
        call print_uint_test
        mov rax, 60
        pop rdi
        syscall