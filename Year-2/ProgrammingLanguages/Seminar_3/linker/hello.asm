global _start
section .data
message: db  'hello, world!', 10
      
section .text
_start:
    mov  rsi, message 