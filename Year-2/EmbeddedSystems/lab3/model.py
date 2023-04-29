import numpy as np
import tensorflow as tf
from tensorflow.keras import layers
from tensorflow.keras.utils import to_categorical

(x_train, y_train), (x_test, y_test) = tf.keras.datasets.mnist.load_data()

mask_1 = np.where(y_train == 0)
mask_2 = np.where(y_train == 1)
mask_3 = np.where(y_train == 2)

mask = np.concatenate((mask_1[0], mask_2[0], mask_3[0]))

x_train = x_train[mask]
y_train = y_train[mask]

# Нормализация данных изображений
x_train = x_train.astype("float32") / 255
x_test = x_test.astype("float32") / 255

# Расширение размерности данных
x_train = np.expand_dims(x_train, -1)
x_test = np.expand_dims(x_test, -1)

# Преобразование меток классов в категориальные
y_train = to_categorical(y_train, 10)
y_test = to_categorical(y_test, 10)

def create_model():
    model = tf.keras.Sequential([
        layers.Input(shape=(28, 28, 1)),
        layers.Conv2D(32, kernel_size=(3, 3), activation="relu"),
        layers.MaxPooling2D(pool_size=(2, 2)),
        layers.Conv2D(64, kernel_size=(3, 3), activation="relu"),
        layers.MaxPooling2D(pool_size=(2, 2)),
        layers.Flatten(),
        layers.Dropout(0.5),
        layers.Dense(10, activation="softmax")
    ])

    model.compile(loss="categorical_crossentropy", optimizer="adam", metrics=["accuracy"])
    return model

model = create_model()

batch_size = 128
epochs = 15

model.fit(x_train, y_train, batch_size=batch_size, epochs=epochs, validation_split=0.1)

score = model.evaluate(x_test, y_test, verbose=0)
print("Test loss:", score[0])
print("Test accuracy:", score[1])

model.save("mnist_cnn_model.h5")