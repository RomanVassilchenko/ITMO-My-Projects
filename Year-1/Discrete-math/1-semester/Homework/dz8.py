import sys
import math


def choose_letter(letter):
    while len(letter) != 4:
        letter = '0' + letter
    if letter[0] == "0":
        if letter[1] == "0":
            if letter[2] == '0':
                if letter[3] == '0':
                    return '0'
                else:
                    return '1'
            else:
                if letter[3] == '0':
                    return '2'
                else:
                    return '3'
        else:
            if letter[2] == '0':
                if letter[3] == '0':
                    return '4'
                else:
                    return '5'
            else:
                if letter[3] == '0':
                    return '6'
                else:
                    return '7'
    else:
        if letter[1] == '0':
            if letter[2] == '0':
                if letter[3] == '0':
                    return '8'
                else:
                    return '9'
            else:
                if letter[3] == '0':
                    return 'A'
                else:
                    return 'B'
        else:
            if letter[2] == '0':
                if letter[3] == '0':
                    return 'C'
                else:
                    return 'D'
            else:
                if letter[3] == '0':
                    return 'E'
                else:
                    return 'F'


def convert_from_16_to_10(number):
    result = 0
    comma = False
    position_comma = len(number)
    for i in range(len(number)):
        if number[i] == '.':
            position_comma = i
    int_number = number[:position_comma]
    for i in range(len(number)):
        if comma:
            if number[i] == '1':
                result += 1 / (16 ** (i - len(int_number)))
            elif number[i] == '2':
                result += 2 / (16 ** (i - len(int_number)))
            elif number[i] == '3':
                result += 3 / (16 ** (i - len(int_number)))
            elif number[i] == '4':
                result += 4 / (16 ** (i - len(int_number)))
            elif number[i] == '5':
                result += 5 / (16 ** (i - len(int_number)))
            elif number[i] == '6':
                result += 6 / (16 ** (i - len(int_number)))
            elif number[i] == '7':
                result += 7 / (16 ** (i - len(int_number)))
            elif number[i] == '8':
                result += 8 / (16 ** (i - len(int_number)))
            elif number[i] == '9':
                result += 9 / (16 ** (i - len(int_number)))
            elif number[i] == 'A':
                result += 10 / (16 ** (i - len(int_number)))
            elif number[i] == 'B':
                result += 11 / (16 ** (i - len(int_number)))
            elif number[i] == 'C':
                result += 12 / (16 ** (i - len(int_number)))
            elif number[i] == 'D':
                result += 13 / (16 ** (i - len(int_number)))
            elif number[i] == 'E':
                result += 14 / (16 ** (i - len(int_number)))
            elif number[i] == 'F':
                result += 15 / (16 ** (i - len(int_number)))
        else:
            if number[i] == '1':
                result += 1 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '2':
                result += 2 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '3':
                result += 3 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '4':
                result += 4 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '5':
                result += 5 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '6':
                result += 6 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '7':
                result += 7 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '8':
                result += 8 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '9':
                result += 9 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == 'A':
                result += 10 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == 'B':
                result += 11 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == 'C':
                result += 12 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == 'D':
                result += 13 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == 'E':
                result += 14 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == 'F':
                result += 15 * 16 ** (len(int_number) - 1 - i)
            elif number[i] == '.':
                comma = True
    return round(result, 3)


def convert_from_2_to_10(number):
    result = 0
    comma = False
    position_comma = len(number)
    for i in range(len(number)):
        if number[i] == '.':
            position_comma = i
    int_number = number[:position_comma]
    for i in range(len(number)):
        if comma:
            if number[i] == '1':
                result += 1 / (2 ** (i - len(int_number)))
        else:
            if number[i] == '1':
                result += 2 ** (len(int_number) - 1 - i)
            elif number[i] == '.':
                comma = True
    return round(result, 3)


def convert_from_16_to_2(number):
    result = ""
    for i in number:
        if i == '0':
            result += '0000'
        elif i == '1':
            result += '0001'
        elif i == '2':
            result += '0010'
        elif i == '3':
            result += '0011'
        elif i == '4':
            result += '0100'
        elif i == '5':
            result += '0101'
        elif i == '6':
            result += '0110'
        elif i == '7':
            result += '0111'
        elif i == '8':
            result += '1000'
        elif i == '9':
            result += '1001'
        elif i == 'A':
            result += '1010'
        elif i == 'B':
            result += '1011'
        elif i == 'C':
            result += '1100'
        elif i == 'D':
            result += '1101'
        elif i == 'E':
            result += '1110'
        else:
            result += '1111'
    return result


# перевод чисел
def convert(number, base):
    sign = 0
    if number < 0:
        sign = 1
    number = abs(number)
    float_number, int_number = math.modf(number)
    whole, balance = int_number, 0  # целая часть и остаток
    whole_conv, fract_conv = [], []  # массив конвертированных целой и дробной частей
    while whole > 0:
        balance = whole % 2
        whole = whole // 2  # перевод целой части в двоичную
        whole_conv.append(int(balance))
    whole_conv.reverse()
    degree, fract, count_of_one, no_one = -1, 0, 0, True
    while len(whole_conv) + count_of_one < 12 and fract != float_number:
        if fract + 2 ** degree <= float_number:
            if no_one:
                no_one = False
            fract += 2 ** degree  # перевод дробной части
            fract_conv.append(1)
        else:
            fract_conv.append(0)
        if not no_one:
            count_of_one += 1
        degree -= 1
    if len(whole_conv) == 0:
        whole_conv.append(0)
    result = ""
    if sign == 1:
        result += "-"
    if base == 2:
        for num in whole_conv:  # преобразуем число
            result += str(num)
        result += '.'
        for num in fract_conv:
            result += str(num)
        return result
    if base == 16:
        inc, step = len(whole_conv) % 4, 4
        if inc != 0:
            letter = ""
            for i in range(0, inc):
                letter += str(whole_conv[i])
            result += choose_letter(letter)
        while inc + step <= len(whole_conv):  # переводим целую часть
            letter = ""
            for i in range(inc, inc + step):
                letter += str(whole_conv[i])
            inc += step
            result += choose_letter(letter)
        result += '.'
        inc = 0
        while inc + step <= len(fract_conv):
            letter = ""
            for i in range(inc, inc + step):  # переводим дробную часть
                letter += str(fract_conv[i])
            result += choose_letter(letter)
            inc += step
        while len(fract_conv) - inc != step:
            fract_conv.append(0)
        letter = ""
        for i in range(inc, inc + step):
            letter += str(fract_conv[i])
        result += choose_letter(letter)
        return result


# парсим в форматы
def parse_to(number, format):
    sign = 0
    if number[0] == '-':
        number = number[1:]
        sign = 1
    if format == 1:
        if number[0] == '0':
            order = 0  # порядок
            for i in range(2, len(number)):
                if number[i] != '0':
                    break
                order -= 1
            number = number[:2] + number[2 - order:]  # преобразуем
        else:
            order = 0
            for i in number:
                if i == '.':
                    break
                order += 1
            number = "0." + number[:order] + number[order + 1:]
        carry = False
        if number[4] == '9' or number[4] == 'A' or number[4] == 'B' or number[4] == 'C' or number[4] == 'D' or number[
            4] == 'E' or number[4] == 'F':
            carry = True
        number = number[:4]
        if carry:
            numb = '0' + convert_from_16_to_2(number[2:])
            number = '0.' + choose_letter(sum_str(numb, '000000001')[1:5]) + choose_letter(
                sum_str(numb, '000000001')[5:])
        characteristic = 64 + order
        characteristic_str = convert(characteristic, 2)[:-1]
        while len(characteristic_str) < 7:
            characteristic_str = '0' + characteristic_str
        mantiss = convert_from_16_to_2(number[2:])
        while len(mantiss) < 8:
            mantiss += '0'
        return [str(sign) + '.' + characteristic_str + '.' + mantiss, number, characteristic]
    elif format == 2:
        order = 0
        if number[0] == '0':
            for i in range(2, len(number)):
                if number[i] != '0':
                    break
                order -= 1
            number = number[:2] + number[2 - order:]  # преобразуем
        else:
            for i in number:
                if i == '.':
                    break
                order += 1
            number = "0." + number[:order] + number[order + 1:]
        carry = False
        if number[10] == '1':
            carry = True
        number = number[:10]
        if carry:
            number = '0.' + sum_str('0' + number[2:], '000000001')[1:]
        characteristic = 128 + order
        characteristic_str = convert(characteristic, 2)[:-1]
        while len(characteristic_str) < 8:
            characteristic_str = '0' + characteristic_str
        return [str(sign) + '.' + characteristic_str + '.' + number[3:], number, characteristic]


def sum_str(number1, number2):  # операнды обязательно 9-разрядные строки
    carry = False  # перенос
    result = ""
    for i in range(8, -1, -1):
        if number1[i] == '1' and number2[i] == '1':
            if carry:
                result = '1' + result
            else:
                result = '0' + result
                carry = True
        elif (number1[i] == '1' and number2[i] == '0') or (number1[i] == '0' and number2[i] == '1'):
            if carry:
                result = '0' + result
            else:
                result = '1' + result
        else:
            if carry:
                result = '1' + result
                carry = False
            else:
                result = '0' + result
    return result


def dop_code(number):  # операнд - 9-разрядная строка
    result = ""
    for i in range(8, -1, -1):
        if number[i] != '1':
            result = '1' + result
        else:
            result = '0' + result
    return sum_str(result, '000000001')


# делимое(9), частное(8), чмсло разрядов сдвига, вправо?
def shift(number1, number2, count, right):
    if count > 8:
        return ["0", "0"]
    divident, private = "", ""  # делимое и частное
    if right:
        for i in range(count):
            divident += number1[0]
        number = number1 + number2
        for i in range(len(number)):
            if len(divident) < 9:
                divident += number[i]
            else:
                if len(private) < 8:
                    private += number[i]
                else:
                    break
        return [divident, private]
    else:
        number = number1[count:] + number2
        for i in range(len(number)):
            if len(divident) < 9:
                divident += number[i]
            else:
                private += number[i]
        while len(private) < 8:
            private += '0'
        return [divident, private]


def check_divident_and_private(number1, number2):
    if number1[0] == '0':
        return number2[:-1] + '1'
    else:
        return number2


if len(sys.argv) == 3:
    print("Деление в формате Ф1:")
    dividend, divider = float(sys.argv[1]), float(sys.argv[2])  # делимое и делитель
    dividend_str16, divider_str16 = convert(dividend, 16), convert(divider, 16)
    dividend_str2, divider_str2 = convert(dividend, 2), convert(divider, 2)
    dividend_f1, divider_f1 = parse_to(dividend_str16, 1), parse_to(divider_str16, 1)
    dividend_f2, divider_f2 = parse_to(dividend_str2, 2), parse_to(divider_str2, 2)
    print("\nA = " + str(dividend) + " = (" + dividend_str16 + ")16 = (" + dividend_f1[
        1] + ")16 * 16^(" + str(dividend_f1[2] - 64) + ")")
    print(
        "B = " + str(divider) + " = (" + divider_str16 + ")16 = (" + divider_f1[1] + ")16 * 16^(" + str(
            divider_f1[2] - 64) + ")")
    charact_a, charact_b = dividend_f1[2], divider_f1[2]
    charact_a_str, charact_b_str = str(charact_a), str(divider_f1[2])
    print("Xc = Xa - Xb + d")
    print("Xc = " + charact_a_str + " - " +
          charact_b_str + " + 64 = " + str(charact_a - charact_b + 64))
    print("Pc = " + str(charact_a - charact_b))
    order = charact_a - charact_b
    print("N шага | Действие |   Делимое   |   Частное")
    print('-' * 45)
    mantiss_a, mantiss_b = '0' + dividend_f1[0][10:], '0' + divider_f1[0][10:]
    mantiss_b_dop = dop_code(mantiss_b)
    private = '00000000'
    print("   0   |    Ma    |  " + mantiss_a + "  |   " + private)
    print("       | [-Mb]доп |  " + mantiss_b_dop + "  |")
    divident = sum_str(mantiss_b_dop, mantiss_a)
    if divident[0] == '0':
        order += 1
        print("       |    R0    |  " + divident + "  |     R0>0")
        divident = shift(mantiss_a, '00000000', 4, True)[0]
        private = shift(mantiss_a, '00000000', 4, True)[1]
        print("       |  Ma ->4  |  " + divident + "  |   " + private)
        print("       | [-Mb]доп |  " + mantiss_b_dop + "  |")
        divident = sum_str(divident, mantiss_b_dop)
        print("       |    R0    |  " + divident + "  |   " + private)
    else:
        print("       |    R0    |  " + divident + "  |   " + private)
    print('-' * 45)
    for i in range(8):
        operand = mantiss_b
        if private[7] == '1':
            operand = mantiss_b_dop
        divident_fix, private_fix = divident, private
        divident = shift(divident_fix, private_fix, 1, False)[0]
        private = shift(divident_fix, private_fix, 1, False)[1]
        print("   " + str(i + 1) + "   |   <-R" + str(i) + "   |  " + divident + "  |   " + private)
        if operand == mantiss_b:
            print("       |  [Mb]пр  |  " + mantiss_b + "  |")
        else:
            print("       | [-Mb]доп |  " + mantiss_b_dop + "  |")
        divident = sum_str(divident, operand)
        private = check_divident_and_private(divident, private)
        print("       |    R" + str(i + 1) + "    |  " + divident + "  |   " + private)
        print('-' * 45)
    private = choose_letter(private[:4]) + choose_letter(private[4:])
    result_16 = private
    if order > 0:
        if order > len(private):
            result_16 = private + '0' * (order - len(private))
        elif order < len(private):
            result_16 = private[0] + '.' + private[1]
    elif order < 0:
        result_16 = '0.' + '0' * abs(order) + private
    result = convert_from_16_to_10(result_16)
    print("C* = " + '(0.' + private + ')16 * 16^(' + str(order) + ') = (' + result_16 + ")16 = " + str(result))
    c_real = round(dividend / divider, 3)
    print("dC = Cт - C* = " + str(c_real) + " - " + str(result) + " = " + str(round(
        c_real - result, 3)) + "      (абслолютная погрешность)")
    print('ъС = |dC/Cт| * 100% = |' + str(round(c_real - result, 3)) + '/' + str(c_real) + '| * 100% = ' + str(
        round(abs((c_real - result) / c_real) * 100, 2)) + '%      (относительная погрешность)')
    print("Погрешность вызвана неточным представлением операндов\n")

    print("Деление в формате Ф2:")
    print("\nA = " + str(dividend) + " = (" + dividend_str2 + ")2 = (" + dividend_f2[
        1] + ")2 * 2^(" + str(dividend_f2[2] - 128) + ")")
    print(
        "B = " + str(divider) + " = (" + divider_str2 + ")2 = (" + divider_f2[1] + ")2 * 2^(" + str(
            divider_f2[2] - 128) + ")")
    charact_a, charact_b = dividend_f2[2], divider_f2[2]
    charact_a_str, charact_b_str = str(charact_a), str(charact_b)
    print("Xc = Xa - Xb + d")
    print("Xc = " + charact_a_str + " - " +
          charact_b_str + " + 128 = " + str(charact_a - charact_b + 128))
    print("Pc = " + str(charact_a - charact_b))
    order = charact_a - charact_b
    print("N шага | Действие |   Делимое   |   Частное")
    print('-' * 45)
    mantiss_a, mantiss_b = '01' + dividend_f2[0][11:], '01' + divider_f2[0][11:]
    mantiss_b_dop = dop_code(mantiss_b)
    private = '00000000'
    print("   0   |    Ma    |  " + mantiss_a + "  |   " + private)
    print("       | [-Mb]доп |  " + mantiss_b_dop + "  |")
    divident = sum_str(mantiss_b_dop, mantiss_a)
    steps = 8
    if divident[0] == '0':
        steps = 7
        order += 1
        private = '00000001'
        print("       |    R0    |  " + divident + "  |   " + private)
    else:
        print("       |    R0    |  " + divident + "  |   " + private)
    print('-' * 45)
    for i in range(steps):
        operand = mantiss_b
        if private[7] == '1':
            operand = mantiss_b_dop
        divident_fix, private_fix = divident, private
        divident = shift(divident_fix, private_fix, 1, False)[0]
        private = shift(divident_fix, private_fix, 1, False)[1]
        print("   " + str(i + 1) + "   |   <-R" + str(i) + "   |  " + divident + "  |   " + private)
        if operand == mantiss_b:
            print("       |  [Mb]пр  |  " + mantiss_b + "  |")
        else:
            print("       | [-Mb]доп |  " + mantiss_b_dop + "  |")
        divident = sum_str(divident, operand)
        private = check_divident_and_private(divident, private)
        print("       |    R" + str(i + 1) + "    |  " + divident + "  |   " + private)
        print('-' * 45)
    result_2 = private
    if order > 0:
        if order > len(private):
            result_2 = private + '0' * (order - len(private))
        elif order < len(private):
            result_2 = private[:order] + '.' + private[order:]
    elif order < 0:
        result_2 = '0.' + '0' * abs(order) + private
    result = convert_from_2_to_10(result_2)
    print("C* = " + '(0.' + private + ')2 * 2^(' + str(order) + ') = (' + result_2 + ")2 = " + str(result))
    print("dC = Cт - C* = " + str(c_real) + " - " + str(result) + " = " + str(round(
        c_real - result, 3)) + "      (абслолютная погрешность)")
    print('ъС = |dC/Cт| * 100% = |' + str(round(c_real - result, 3)) + '/' + str(c_real) + '| * 100% = ' + str(
        round(abs((c_real - result) / c_real) * 100, 2)) + '%      (относительная погрешность)')
    print("Погрешность вызвана неточным представлением операндов\n")
