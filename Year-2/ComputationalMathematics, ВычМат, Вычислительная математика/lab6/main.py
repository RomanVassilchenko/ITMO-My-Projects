import numpy as np
import matplotlib.pyplot as plt
from tabulate import tabulate

def exact_solution_f1(x):
    return 2 * np.exp(x) - x - 1

def exact_solution_f2(x):
    return 1.49*np.exp(-x) + np.sin(x) / 2 - np.cos(x) / 2

def euler(f, y0, x0, xn, h):
    n = int((xn - x0) / h)
    x = np.linspace(x0, xn, n+1)
    y = np.zeros(n+1)
    y[0] = y0

    for i in range(n):
        y[i+1] = y[i] + h * f(x[i], y[i])

    return x, y

def improved_euler(f, y0, x0, xn, h):
    n = int((xn - x0) / h)
    x = np.linspace(x0, xn, n+1)
    y = np.zeros(n+1)
    y[0] = y0

    for i in range(n):
        k1 = f(x[i], y[i])
        k2 = f(x[i] + h, y[i] + h * k1)
        y[i+1] = y[i] + h * (k1 + k2) / 2

    return x, y

def adams(f, y0, x0, xn, h, m):
    n = int((xn - x0) / h)
    x = np.linspace(x0, xn, n+1)
    y = np.zeros(n+1)
    y[0] = y0

    for i in range(m):
        y[i+1] = y[i] + h * f(x[i], y[i])

    for i in range(m, n):
        y[i+1] = y[i] + h * (55*f(x[i], y[i]) - 59*f(x[i-1], y[i-1]) + 37*f(x[i-2], y[i-2]) - 9*f(x[i-3], y[i-3])) / 24

    return x, y

def runge_rule(f, y0, x0, xn, h1, h2, p):
    n1 = int((xn - x0) / h1)
    n2 = int((xn - x0) / h2)
    y_h1 = np.zeros(n1+1)
    y_h2 = np.zeros(n2+1)

    _, y_h1 = euler(f, y0, x0, xn, h1)
    _, y_h2 = euler(f, y0, x0, xn, h2)

    return np.abs(y_h1[-1] - y_h2[-1]) / (2**p - 1)

def main():
    f1 = lambda x, y: x + y
    f2 = lambda x, y: np.sin(x) - y
    f3 = lambda x, y: np.exp(-x) - y**2

    print("f1 = x + y")
    print("f2 = sin(x) - y")
    print("f3 = e^(-x) - y^2")
    func_input = input("Выберите функцию f1/f2/f3: ")

    func = f1

    if func_input == "f1":
        func = f1
        exact_solution = exact_solution_f1
    elif func_input == "f2":
        func = f2
        exact_solution = exact_solution_f2
    elif func_input == "f3":
        func = f3
        exact_solution = None
    else:
        main()
        return

    epsilon = runge_rule(func, 1, 0, 1, 0.1, 0.05, 1)
    print(f"Точность метода Эйлера по правилу Рунге: {epsilon}")

    x0, xn, h = 0, 1, 0.1

    x_exact = np.arange(x0, xn + h, h)
    if exact_solution is not None:
        y_exact = exact_solution(x_exact)
        plt.plot(x_exact, y_exact, label='Exact solution')

    x_euler, y_euler = euler(func, 1, 0, 1, 0.1)
    plt.plot(x_euler, y_euler, label='Euler')

    x_improved_euler, y_improved_euler = improved_euler(func, 1, 0, 1, 0.1)
    plt.plot(x_improved_euler, y_improved_euler, label='Improved Euler')

    x_adams, y_adams = adams(func, 1, 0, 1, 0.1, 4)
    plt.plot(x_adams, y_adams, label='Adams')

    # Подготовка данных для таблицы
    table_data = list(zip(x_euler, y_euler, y_improved_euler, y_adams))

    # Вывод таблицы с использованием tabulate
    print(tabulate(table_data, headers=["x", "Euler", "Improved Euler", "Adams"]))

    epsilon = runge_rule(func, 1, 0, 1, 0.1, 0.05, 1)
    print(f"\nТочность метода Эйлера по правилу Рунге: {epsilon}\n")

    plt.legend()
    plt.show()


if __name__ == "__main__":
    main()

