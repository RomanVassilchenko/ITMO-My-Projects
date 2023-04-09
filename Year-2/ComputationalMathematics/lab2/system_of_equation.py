import numpy as np
import matplotlib.pyplot as plt
from scipy.optimize import fsolve

#Test
def system_1(xy):
    x, y = xy
    return [x**2 + y**2 - 1, x**2 - y - 0.5]

def system_2(xy):
    x, y = xy
    return [x**2 + y**2 - 1, x - y**2]

def plot_system(system):
    x = np.linspace(-2, 2, 400)
    y = np.linspace(-2, 2, 400)
    X, Y = np.meshgrid(x, y)

    Z1 = np.array([system([x_, y_])[0] for x_, y_ in zip(np.ravel(X), np.ravel(Y))]).reshape(X.shape)
    Z2 = np.array([system([x_, y_])[1] for x_, y_ in zip(np.ravel(X), np.ravel(Y))]).reshape(X.shape)

    plt.contour(X, Y, Z1, levels=[0], colors='r')
    plt.contour(X, Y, Z2, levels=[0], colors='b')
    plt.xlabel('x')
    plt.ylabel('y')
    plt.show()

def simple_iteration(system, x0, y0, epsilon=1e-5, max_iter=1000):
    def jacobian(xy):
        x, y = xy
        return np.array([[2*x, 2*y], [2*x, -1]])

    xy = np.array([x0, y0], dtype=float)
    for i in range(max_iter):
        J_inv = np.linalg.inv(jacobian(xy))
        F = np.array(system(xy))
        xy_next = xy - np.dot(J_inv, F)
        error = np.linalg.norm(xy_next - xy)

        if error < epsilon:
            return xy_next, i + 1, error

        xy = xy_next

    raise Exception("Solution not found in {} iterations".format(max_iter))

if __name__ == "__main__":
    systems = {'1': [system_1,"x**2 + y**2 - 1, x**2 - y - 0.5"], '2': [system_2,"x**2 + y**2 - 1, x - y**2"]}
    print("Выберите систему уравнений:")
    for key, value in systems.items():
        print(key, value[1])

    choice = input("Введите номер системы: ")
    system = systems[choice][0]
    
    x0, y0 = map(float, input("Введите начальные приближения x0, y0: ").split())
    xy_solution, iterations, error = simple_iteration(system, x0, y0)
    print(f"Вектор неизвестных: x1 = {xy_solution[0]:.5f}, x2 = {xy_solution[1]:.5f}")
    print(f"Количество итераций: {iterations}")
    print(f"Вектор погрешностей: {error:.5e}")

    residuals = np.abs(np.array(system(xy_solution)))
    print("Проверка решения системы уравнений:")
    print(f"Невязки: {residuals[0]:.5e}, {residuals[1]:.5e}")

    plot_system(system)