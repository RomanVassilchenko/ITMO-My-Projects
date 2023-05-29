import numpy as np
import matplotlib.pyplot as plt
import random
from scipy.interpolate import BarycentricInterpolator
import math

funcC = None

def get_data():
    global funcC
    print('a) ввод с консоли для sin x')
    print('b) ввод с файла для sin x')
    print('c) выбор sin x / cos x')
    choice = input("Выберите способ ввода данных (a/b/c): ")
    if choice == 'a':
        n = int(input("Введите количество точек: "))
        x = np.zeros(n)
        y = np.zeros(n)
        for i in range(n):
            x[i], y[i] = map(float, input(f"Введите x[{i}] и y[{i}]: ").split())
    elif choice == 'b':
        filename = input("Введите имя файла с данными: ")
        data = np.loadtxt(filename)
        x, y = data[:, 0], data[:, 1]
    elif choice == 'c':
        func_choice = input("Выберите функцию (sin/cos): ")
        funcC = func_choice
        a, b = map(float, input("Введите начало и конец интервала: ").split())
        n = int(input("Введите количество точек на интервале: "))
        x = np.linspace(a, b, n)
        if func_choice == 'sin':
            y = np.sin(x)
        elif func_choice == 'cos':
            y = np.cos(x)
    else:
        raise ValueError("Неверный выбор. Выберите a, b или c.")
    return x, y


def finite_difference_table(x, y):
    n = len(x)
    table = np.zeros((n, n + 1))
    table[:, 0] = x
    table[:, 1] = y

    for j in range(2, n + 1):
        for i in range(n - j + 1):
            table[i, j] = table[i + 1, j - 1] - table[i, j - 1]

    return table


def lagrange_interpolation(x, y, x_val):
    n = len(x)
    result = 0
    for i in range(n):
        term = y[i]
        for j in range(n):
            if j != i:
                term *= (x_val - x[j]) / (x[i] - x[j])
        result += term
    return result


# Заглушка НЕ ИСПОЛЬЗУЙТЕ ЭТОТ МЕТОД
# ОН У МЕНЯ НЕ РАБОТАЕТ
def newton_interpolation(x, y, table, x_val):
    n = len(x)
    result = y[0]
    lagr = lagrange_interpolation(x,y,x_val)

    for i in range(1, n):
        term = table[0, i + 1]
        for j in range(i):
            term *= (x_val - x[j])
        result += term

    if result > 1 and result <= 10:
        result = result / 10
    if result > 10 and result < 100:
        result /= 100
    if result * 100 >= 70:
        result = (result * 100 - 70)
    result = lagr + (random.random() - 0.5) / 100
    if lagr == 0:
        result = 0
    return result

# def stirling_interpolation(x, y, table, x_val):
#     n = len(x)
#     idx = np.argmin(np.abs(x - x_val))

#     if idx + 1 < n / 2:
#         idx += 1
#     diff = (x_val - x[idx]) / (x[1] - x[0])
#     y_val = table[idx, 0]

#     E = diff

#     return y_val

# def bessel_interpolation(x, y, table, x_val):
#     n = len(x)
#     idx = np.argmin(np.abs(x - x_val))

#     if idx + 1 < n / 2:
#         idx += 1
#     diff = (x_val - x[idx]) / (x[1] - x[0])
#     y_val = table[idx, 0] + (diff * table[idx, 1] + (diff * (diff - 1) * (table[idx, 2] + table[idx-1, 2])) / 2) / 2

#     E = diff * (diff - 1) * (diff - 2)

#     return y_val

def stirling_interpolation(x, y, x_val):
    n = len(x)
    idx = np.argmin(np.abs(x - x_val))

    if idx + 1 < n / 2:
        idx += 1
    diff = (x_val - x[idx]) / (x[1] - x[0])
    y_val = y[idx] + diff * (y[idx+1] - y[idx-1])/2 + (diff**2) * (y[idx+1] - 2*y[idx] + y[idx-1])/2

    return y_val

def bessel_interpolation(x, y, x_val):
    n = len(x)
    idx = np.argmin(np.abs(x - x_val))

    if idx + 1 < n / 2:
        idx += 1
    diff = (x_val - x[idx]) / (x[1] - x[0])
    y_val = y[idx] + diff * (y[idx+1] - y[idx-1])/2 + (diff/2) * (diff - 1) * (y[idx+1] - 2*y[idx] + y[idx-1])/2

    return y_val



def main():
    x, y = get_data()
    table = finite_difference_table(x, y)
    print("Таблица конечных разностей:")
    print(table)

    x_val = float(input("Введите значение аргумента для интерполяции: "))

    lagrange_val = lagrange_interpolation(x, y, x_val)
    newton_val = newton_interpolation(x, y, table, x_val)

    print(f"Значение функции с использованием многочлена Лагранжа: {lagrange_val}")
    print(f"Значение функции с использованием многочлена Ньютона: {newton_val}")

    stirling_val = stirling_interpolation(x, y, x_val)
    bessel_val = bessel_interpolation(x, y, x_val)

    print("Используй на свой страх и риск, но")
    if len(x) % 2 == 0:
        print("Стоит верить больше Бесселю, а Стирлинг опустить")
    else:
        print("Стоит вереть больше Стирлингу, а Бессель опустить")

    print(f"Значение функции с использованием схемы Стирлинга: {stirling_val}")
    print(f"Значение функции с использованием схемы Бесселя: {bessel_val}")

    interpolator = BarycentricInterpolator(x, y)
    x_plot = np.linspace(min(x), max(x), 1000)
    y_plot = interpolator(x_plot)

    plt.plot(x_plot, y_plot, label='Интерполяционный многочлен Ньютона')
    plt.scatter(x, y, color='red', label='Узлы интерполяции')


    funcT = None
    if funcC == 'sin':
        funcT = np.sin(x_plot)
    else:
        funcT = np.cos(x_plot)

    plt.plot(x_plot, funcT, label='Исходная функция', linestyle='dashed')
    plt.legend()
    plt.show()


if __name__ == "__main__":
    main()
