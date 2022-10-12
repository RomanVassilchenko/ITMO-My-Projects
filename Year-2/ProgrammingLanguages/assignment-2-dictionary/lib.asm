section .text
 
global exit
global string_length
global print_string
global print_char
global print_newline
global print_uint
global print_int
global string_equals
global read_char
global read_word
global parse_uint
global parse_int
global string_copy
 
; Принимает код возврата и завершает текущий процесс
exit:
    mov rax, 60
    syscall
    ret 

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    xor rax, rax
    .loop:
        cmp byte [rdi + rax], 0 ; Сравниваем элемент [rdi + rax] с 0x0
        je .return              ; Если равна нуль-терминатору, то выход
        inc rax                 ; Иначе увеличиваем rax (количество символов)
        jmp .loop
    .return:
        ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    call string_length      ; Получаем длину переданной строки -> rax
    mov rdx, rax            ; Переносим длину строки из rax в rdx
    mov rsi, rdi            ; Переносим строку в rsi
    mov rdi, 1
    mov rax, 1
    syscall 
    ret

; Принимает код символа и выводит его в stdout
print_char:
    mov rax, 1
    mov rdx, 1
    push rdi            ; Сдвигаем стек на один символ (Который хотим распечатать)
    mov rdi, 1
    mov rsi, rsp        ; Получаем символ в rsi
    syscall
    pop rdi             ; Сдвигаем стек обратно
    ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rdi, 10
    call print_char
    ret

; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    mov rdx, rdi        ; Перенос строки -> rdx
	xor rcx, rcx		; Счетчик числа / Длина строка
    push 0         		; 0-терминатор для будущего числа
    .loop:	       
        dec rsp		    ; Уменьшаем стек на 1
	    inc rcx         ; Увеличиваем rcx на 1
        mov rax, rdx	; Перенос строки -> rax 
        xor rdx, rdx    ; Обнуление rdx

        mov r11, 10     ; Деление rax
        div r11         ; на 10

        add rdx, '0'	; Перевод полученного числа в ASCII
        mov [rsp], dl   ; Перенос в стек dl (младший байт rdx)
        mov rdx, rax    ; Перенос rax -> rdx
        cmp rdx, 0		; Если при делении осталась целая часть = 0 ==> конец цикла
        jne .loop                 
    mov rdi, rsp   		; Перенос rsp -> rdi (аргумент print_string - адрес начала числа)
	push rcx		    ; Сохраняем счетчик
	call print_string
    pop rcx	            ; Возвращаем счетчик
	add rsp, rcx   		; Возврат rsp в начальное положение
	pop rax	       		; Удаление 0-терминатор из стека
	ret	      


; Выводит знаковое 8-байтовое число в десятичном формате 
print_int:
    cmp rdi, 0          ; Сравниваем число с 0
	jnl print_uint      ; Если число >= 0, то print_uint
	push rdi            ; Сохраняем rdi в стек
	mov rdi, '-'        ; Закидываем в стек символ минуса
	call print_char     ; Выводим его через print_char
	pop rdi             ; Возвращаем обратно прошлое значение rdi
	neg rdi             ; rdi = rdi * (-1)
	jmp print_uint      ; Выводим число, как uint


; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:
	xor rax, rax                    ; Обнуляем счетчик по строке 
	.loop:				;сравниваю поэлементно строки, конец - совпадение терминаторов
	    mov dl, byte [rsi+rax]      ; Закидываем в dl [rsi+rax]
	    cmp byte [rdi+rax], dl      ; Сравниваем dl с второй строкой [dri + rax]
	    jne .false                  ; Если они не равны, то 0
        cmp byte [rdi+rax], 0       ; Если равны и равны нуль-терминатору, то выход с 1
	    je .true
	    inc rax                     ; Если равны, но не нуль-терминатор, то увеличиваем счетчик rax на 1
	    jmp .loop                   ; И повторяем снова
	.false:
        xor rax, rax
	    ret
    .true:
	    mov rax, 1
	    ret	


; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    xor rax, rax
    push rax
    mov rsi, rsp
    mov rdx, 1
    mov rdi, 0
    mov rax, 0
    syscall
    pop rax
    ret 

; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор

read_word:
    push rdi                        ; Сохранение rdi
    push r12                        ; Сохранение r12
    mov r12, rdi                    ; Перенос адрес начала буфера -> r12
    push r13                        ; Сохранение r13
    mov r13, rsi                    ; Перенос размера буфера -> r13
    .loop_white_spaces:
        call read_char              ; Прочитать символ -> rax
        cmp rax, 0x20               ; Проверка на пробел (0x20)
        je .loop_white_spaces       ;
        cmp rax, 0x09               ; Проверка на таб (0x9)
        je .loop_white_spaces       ;
        cmp rax, 0xA                ; Проверка на перенос строки (0xA)
        je .loop_white_spaces       ;
    .loop:
        cmp rax, 0x0                ; Если нуль-терминатор -> success
        je .success
        cmp rax, 0x20               ; Если пробел -> success
        je .success
        cmp rax, 0x9                ; Если таб -> success
        je .success
        cmp rax, 0xA                ; Если перенос строки -> success
        je .success
        dec r13                     ; Уменьшаем размер
        cmp r13, 0                  ; Сравниваем с нулем
        jbe .overflow               ; Если <= (вышли за размер) -> overflow
        mov byte [r12], al          ; Закидываем в [r12] -> al
        inc r12                     ; Увеличение r12 на 1
        call read_char              ; Чтение символа
        jmp .loop
        
    .success:
        mov byte [r12], 0           ; Закидываем в [r12] нуль-терминатор
        pop r12                     ; Возврат сохраненного r12
        pop r13                     ; Возврат сохраненного r13
        mov rdi, [rsp]              ; Закидываем в rdi значение из rsp
        call string_length          ; Длина строки -> rax
        mov rdx, rax                ; Перенос длины строки -> rdx
        pop rax                     ; Перенос сохраненного rdi -> rax
        ret

    .overflow:
        pop r12                     ; Возврат сохраненного r12
        pop r13                     ; Возврат сохраненного r13
        pop rdi                     ; Возврат сохраненного rdi
        mov rax, 0                  ; При overflow в rax закидывают 0
        ret
 

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    xor rax, rax		    ;   Регистр для числа
	xor rdx, rdx		    ;   Длинна данного числа (Число обозначает сдвиг относительно указателя)
	xor rcx, rcx		    ;   Буфер

	.loop:
	    xor rcx, rcx        ; Обнуление буффера
  	    mov cl, [rdi+rdx]	; Чтение цифры [rdi+rdx]
	    cmp cl, '0'		    ; Если меньше '0', то выход
	    jb .end 
	    cmp cl, '9'         ; Если больше '9', то выход
	    ja .end
	    sub rcx, '0'		; Перевод из ASCII в число

	    mov r11, 10		    ;
	    push rdx            ;
	    mul r11             ; Умножаем прошлое число x10
	    pop rdx             ;

	    add rax, rcx	    ; Получаем новое число rax = rax * 10 + rcx
	    inc rdx             ; Увеличиваем длину числа
	    jmp .loop
	
	.end:
 	    ret




; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
parse_int:
    cmp byte[rdi], '+'	        ; Если первый символ = '+', то парсим
	je .parse
	cmp byte[rdi], '-'          ; Если первый символ не равен '-', то parse_uint, а иначе .parse
	jne parse_uint
	.parse:
	    push rdi                ; Сохраняем указатель в стек
	    inc rdi		            ; Сдвигаем указать на 1, чтобы убрать +/-
	    call parse_uint	        ; Вызываем parse_uint
	    pop rdi                 ; Возвращаем указатель
	    inc rdx                 ; Увеличиваем длину строки на 1 из-за знака
	    cmp byte[rdi], '+'	    ; Если знак +, то возврат, а иначе rax = rax * (-1)
	    je .return
	    neg rax		            ; rax = rax * (-1)
	    .return:
		    ret
 

; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
string_copy:
    call string_length              ; Получаем длину строки
    cmp rdx, rax                    ; Сравниваем длину строки и буфер
    jbe .else                       ; Если меньше, то выход
    mov rcx, rax                    ; Перенос длины строки -> rcx
    .loop:
        test rcx, rcx               ; Проверка на нуль-терминатор
        je .end                 
        dec rcx                     ; rcx = rcx - 1; Пока rcx != 0, то продолжаем
        mov dl, byte [rdi + rcx]    ; Перенос строки в буффер
        mov byte [rsi + rcx], dl
        jmp .loop
    .end:
        mov byte [rsi + rax], 0
        ret
    .else:
        xor rax, rax
        ret   