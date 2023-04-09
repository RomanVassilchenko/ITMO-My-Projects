; task.asm 
  section .data
  messageOut: db  'hello, output!', 10
  messageErr: db  'hello, error!', 10

  section .text
  global _start

  _start:
      mov     rax, 1           ; 'write' syscall number
      mov     rdi, 1           ; stdout descriptor
      mov     rsi, messageOut  ; string address
      mov     rdx, 14          ; string length in bytes
      syscall

      mov	  rax, 1 		   ;
      mov 	  rdi, 2		   ;
      mov	  rsi, messageErr  ;
      mov	  rdx, 13		   ;
      syscall

      mov     rax, 60          ; 'exit' syscall number
      xor     rdi, rdi
      syscall