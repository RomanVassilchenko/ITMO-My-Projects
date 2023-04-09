CFLAGS     = -g -O2 -Wall -Werror -std=c17 -Wno-unused-function -Wdiscarded-qualifiers -Wincompatible-pointer-types -Wint-conversion -fno-plt
CC         = gcc
LD         = gcc

all: $(TARGET)

$(TARGET): vector.o main.o
	$(LD) -o $@ $^

%.o: %.c
	$(CC) -c $(CFLAGS) -o $@ $<

clean: 
	$(RM) $(TARGET) *.o

run:
	./$(TARGET)

.PHONY: clean all run

