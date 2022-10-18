/* print_file.c */
#include <stdio.h>
extern void print_string(char*);
extern void print_file(char*);

int main() {
    print_string("Please enter file name: ");
    char filename[100];
    scanf("%s",filename);
    print_file(filename);
    return 0;
}