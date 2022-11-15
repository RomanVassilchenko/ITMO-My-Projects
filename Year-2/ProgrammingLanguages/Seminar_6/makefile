CFLAGS     = -g -O2 -Wall -Werror -std=c17 -Wno-unused-function -Wdiscarded-qualifiers -Wincompatible-pointer-types -Wint-conversion -fno-plt
CC         = gcc
LD         = gcc
TARGET     = parser

all: $(TARGET)

$(TARGET): ast.o main.o tokenizer.o
	$(LD) -o $@ $^

%.o: %.c
	$(CC) -c $(CFLAGS) -o $@ $<

clean: 
	$(RM) $(TARGET) *.o

run:
	./$(TARGET)

.PHONY: clean all run

