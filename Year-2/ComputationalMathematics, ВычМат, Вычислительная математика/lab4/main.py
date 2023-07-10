import numpy as np
import matplotlib.pyplot as plt
from scipy.optimize import curve_fit
from sklearn.metrics import mean_squared_error
from scipy.stats import pearsonr
import sys

def linear_func(x, a, b):
    return a * x + b


def poly_2(x, a, b, c):
    return a * x**2 + b * x + c


def poly_3(x, a, b, c, d):
    return a * x**3 + b * x**2 + c * x + d


def exponential_func(x, a, b):
    return a * np.exp(b * x)


def logarithmic_func(x, a, b):
    return a * np.log(x) + b


def power_func(x, a, b):
    return a * x**b

def compute_pearson_correlation(x, y, params):
    y_pred = linear_func(x, *params)
    return pearsonr(y, y_pred)


def fit_and_plot(func, name, x, y):
    params, _ = curve_fit(func, x, y)
    plt.plot(x, func(x, *params), label=name)
    return params, mean_squared_error(y, func(x, *params))

def read_data_from_file(filename):
    with open(filename, 'r') as file:
        x = []
        y = []
        for line in file:
            point = line.strip().split()
            if len(point) == 2:
                x.append(float(point[0]))
                y.append(float(point[1]))

    return np.array(x), np.array(y)

def read_data_from_input():
    str = ''
    x = []
    y = []
    while str != 'quit':
        str = input()
        point = str.strip().split()
        if len(point) == 2:
            x.append(float(point[0]))
            y.append(float(point[1]))
    return np.array(x), np.array(y)

def main():
    while True:
        option = input("Напишите 'файл' или 'ввод' ")
        if option == 'файл':
            filename = 'test.txt'
            x, y = read_data_from_file(filename)
            break
        elif option == 'ввод':
            print('Введите quit, чтобы закончить ввод')
            x,y = read_data_from_input()
            break
        else:
            print("Некорректный ввод. Попробуйте еще раз")

    # Функции для исследования
    functions = [
        (linear_func, "Линейная"),
        (poly_2, "Полиноминальная 2-й степени"),
        (poly_3, "Полиноминальная 3-й степени"),
        (exponential_func, "Экспоненциальная"),
        (logarithmic_func, "Логарифмическая"),
        (power_func, "Степенная")
    ]

    best_func = None
    best_mse = float("inf")

    with open('output.txt', 'w') as output:
        option = input("Вывод в 'файл' или в 'терминал'? ")
        if option == 'файл':
            sys.stdout = output
        else:
            print("Выбран вариант вывода в терминал")

        for func, name in functions:
            try:
                params, mse = fit_and_plot(func, name, x, y)
                print(f"{name} функция:")
                print(f"  Коэффициенты: {params}")
                print(f"  Среднеквадратические отклонения: {mse}")
                if func == linear_func:
                    correlation, _ = compute_pearson_correlation(x, y, params)
                    print(f"  Коэффициент корреляции Пирсона: {correlation}\n")
                if mse < best_mse:
                    best_mse = mse
                    best_func = name
            except Exception as e:
                print(f"Error fitting {name} function: {e}\n")

        print(f"Лучшая функция приближения: {best_func}")

    plt.scatter(x, y, label="Вводные точки")
    plt.legend()
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("Приближение функции различными методами")
    plt.show()


if __name__ == "__main__":
    main()

