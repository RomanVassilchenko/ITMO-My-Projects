# Задание создайте библиотеку lib.asm с двумя функциями: print_hex и exit для выхода из приложения. Напишите к ней заголовочный файл. Протестируйте её, запустив функцию из другого файла с меткой _start.

```bash
nasm -f elf64 -o lib.o lib.asm
nasm -f elf64 -o main.o main.asm
ld -o app lib.o main.o
```