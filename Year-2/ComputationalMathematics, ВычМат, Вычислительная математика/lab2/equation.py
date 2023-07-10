import numpy as np
import matplotlib.pyplot as plt
from scipy.misc import derivative

# Определение функций
def f1(x):
    return x ** 2 - 4

def f2(x):
    return np.sin(x)

def f3(x):
    return np.exp(x) - 2

# Здесь добавьте свои функции

functions = [f1, f2, f3]

# Реализация методов
def bisection_method(func, a, b, tol):
    if func(a) * func(b) > 0:
        raise ValueError("Функция должна иметь разные знаки на границах интервала")

    iterations = 0
    while (b - a) / 2 > tol:
        iterations += 1
        c = (a + b) / 2
        if func(c) == 0:
            break
        elif func(c) * func(a) < 0:
            b = c
        else:
            a = c

    return (a + b) / 2, iterations

def newton_method(func, a, b, tol):
    def find_initial_approximation():
        if func(a) * derivative(func, a) > 0:
            return a
        elif func(b) * derivative(func, b) > 0:
            return b
        else:
            raise ValueError("Невозможно выбрать начальное приближение")

    x = find_initial_approximation()
    iterations = 0

    while True:
        iterations += 1
        x_prev = x
        x = x_prev - func(x_prev) / derivative(func, x_prev)

        if abs(x - x_prev) < tol:
            break

    return x, iterations

def fixed_point_iteration(func, a, b, tol):
    def g(x):
        return x - func(x)

    def g_derivative(x):
        return 1 - derivative(func, x)

    if not (g_derivative(a) < 1 and g_derivative(b) < 1):
        raise ValueError("Не выполняется достаточное условие сходимости")

    x = (a + b) / 2
    iterations = 0

    while True:
        iterations += 1
        x_prev = x
        x = g(x_prev)

        if abs(x - x_prev) < tol:
            break

    return x, iterations

# Ввод данных
import sys

def input_data(source='keyboard'):
    if source == 'file':
        file_path = input("Введите путь к файлу с входными данными: ")
        sys.stdin = open(file_path, 'r')

    print("Выберите уравнение:")
    for i, func in enumerate(functions, start=1):
        print(f"{i}. {func.__name__}")

    func_idx = int(input("Введите номер уравнения: ")) - 1
    selected_func = functions[func_idx]

    a = float(input("Введите левую границу интервала a: "))
    b = float(input("Введите правую границу интервала b: "))
    tol = float(input("Введите погрешность: "))

    if a >= b:
        raise ValueError("Неверно задан интервал")

    if source == 'file':
        sys.stdin.close()
        sys.stdin = sys.__stdin__

    return selected_func, a, b, tol


# Вывод результатов
def output_results(func, results, to_file=False):
    root, iterations = results

    if to_file:
        with open("output.txt", "w") as f:
            f.write(f"Найденный корень уравнения: {root}\n")
            f.write(f"Значение функции в корне: {func(root)}\n")
            f.write(f"Число итераций: {iterations}\n")
    else:
        print(f"Найденный корень уравнения: {root}")
        print(f"Значение функции в корне: {func(root)}")
        print(f"Число итераций: {iterations}")

# Графики функций
def plot_function(func, a, b):
    x = np.linspace(a - 0.1 * (b - a), b + 0.1 * (b - a), 1000)
    y = func(x)

    plt.plot(x, y)
    plt.axhline(0, color="black", lw=0.5)
    plt.axvline(0, color="black", lw=0.5)
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title(f"График функции {func.__name__}")
    plt.grid(True)
    plt.show()


def main():
    try:
        file_or_keyboard = input("Напишите keyboard, если хотите ввод через консоль или file, если через файл")
        selected_func, a, b, tol = input_data(file_or_keyboard)

        print("Выберите метод для нахождения корня:")
        print("1. Метод половинного деления")
        print("2. Метод Ньютона")
        print("3. Метод простой итерации")

        method_idx = int(input("Введите номер метода: "))

        if method_idx == 1:
            result = bisection_method(selected_func, a, b, tol)
        elif method_idx == 2:
            result = newton_method(selected_func, a, b, tol)
        elif method_idx == 3:
            result = fixed_point_iteration(selected_func, a, b, tol)
        else:
            raise ValueError("Некорректный номер метода")

        output_choice = input("Вывести результаты на экран или в файл? (screen/file): ")
        if output_choice == "screen":
            output_results(selected_func,result)
        elif output_choice == "file":
            output_results(selected_func,result, to_file=True)
        else:
            raise ValueError("Некорректный выбор вывода результатов")

        plot_function(selected_func, a, b)

    except ValueError as e:
        print(f"Ошибка: {e}")

if __name__ == "__main__":
    main()
