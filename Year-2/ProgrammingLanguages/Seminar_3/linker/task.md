# Задание изучите таблицы символов в файлах из задания с функцией print_hex. Как global и extern влияют на содержимое таблиц символов?
```bash
$ objdump -t ../compiler/main.o
$ objdump -t ../compiler/print_hex.o
```
В файлах [main_elf.txt](main_elf.txt) и [print_hex_elf.txt](print_hex_elf.txt) можно увидеть, что:
1. в print_hex ```global``` делает print_hex - ```*ABS*```
2. в main ```extern``` делает print_hex - ```*UND*```


# Вопрос для чего метка _start помечается как global?

global необходим для компонивщика, который занесет его в таблицу и сможет запустить программу с _start
Если нужно изменить _start На другой, то нужно в компоновщике:

```bash
ld -e another_title -o out a.o
```

# Вопрос могут ли несколько .o-файлов содержать метки с одинаковым именем?
Да, если метки не глобальные (т.е. локальные для каждого файла)