ASM=nasm
ASMFLAGS=-f elf64
LD=ld

%.o: %.asm
	$(ASM) $(ASMFLAGS) -o $@ $<

task1: hello_mmap.o
	ld -o $@ $<

task2: hello_mmap_substr.o
	ld -o $@ $<