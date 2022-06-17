ORG 0x687
WORD 0x0000
; ...

ORG 0x000
I: WORD 0x0100 ; Нынешняя i
J: WORD 0x0100 ; Нынешняя j
IMAX: WORD 0x1101 ; Максимальная i (Верхняя граница)
JMAX: WORD 0x1010 ; Максимальная j (Верхняя граница)
CURR: WORD 0x687 ; Нынешний элемент
MASK: WORD 0x00FF ; Маска для вытаскивания половины слова
WORD1: WORD 0x0000 ; Первое слово
WORD2: WORD 0x0000 ; Второе слово

IFUNCALL: WORD 0x0001 ; I % 3
JFUNCALL: WORD 0x0010 ; J % 2
NUMBER: WORD 0x5BB7 ; Константа для функции
RESULT: WORD 0x0000 ; Результат

RESWORD: WORD ? ; Результат после функции

START: ; Начало функции

LD CURR ; Получение первого слова и вызов функции
SWAB
AND MASK
ST WORD1
PUSH
CALL JOB

POP ; Получение результата и проверка на 0 (Не подходит i или j)
CMP #0x0
BEQ SECONDWORD
ST RESWORD

CALL XORVAL ; Вызов XOR для нового результата

SECONDWORD:
CALL INCINDEX ; Увеличение I и J

LD CURR ; Получение второго слова и вызов функции
AND MASK
ST WORD1
PUSH
CALL JOB

POP ; Получение результата и проверка на 0 (Не подходит i или j)
CMP #0x0
BEQ NEXTWORD
ST RESWORD

CALL XORVAL ; Вызов XOR для нового результата
NEXTWORD:
CALL INCINDEX ; Увеличение I и J


LD CURR ; Увеличение числа массива
INC
ST CURR


JUMP START ; Повтор функции

JOB: ; Функция F(Mi, j)
TMP: WORD ?

LD IFUNCALL ; Проверка чисел на i % 3 и j % 2
CMP 0x0011
BNE: SKIP
LD JFUNCALL
CMP 0x0010
BNE SKIP

POP

CMP #0x12A2 ; Проверка, что функция с данным числом не выйдет за область допустимых значений
BGE OUTOFRANGE

ST TMP ; F(Mi, j) = 3 * Mi,j + 23479
ROL
ADD TMP
ADD NUMBER
PUSH
RET
OUTOFRANGE: ; Если число больше допустимого, то возврат 18457
LD #0x4819
PUSH
RET

SKIP: CLA
RET


INCINDEX: ; Увеличение I и J

LD J ; Проверка J на > 9
CMP JMAX
BNE NOTADDI ; Если J !> 9, то просто J++
LD I ; Если J > 9, то I++ и J = 4
INC
ST I
LD IFUNCALL
INC
ST IFUNCALL
LD 0x0100
ST J

NOTADDI: LD J ; Если J !> 9, то просто J++
INC
ST J

LD I ; Проверка I на > 12
CMP IMAX
BNE NOTADDJ: ; Если I !> 12, то просто I++
HLT ; Останова, если I > 12
NOTADDJ: ; Если I !> 12, то просто I++
LD I
INC
ST I
LD JFUNCALL
INC
ST JFUNCALL
RET

XORVAL: ; Функция XOR для значений из функции
TEMP1: WORD ?
LD RESWORD
NOT
AND RESULT
ST TEMP1
LD RESULT
NOT
AND RESWORD
OR TEMP1
ST RESULT
; Вычислил (возможно неверно), что XOR = (A и !B) или (!A и B)
