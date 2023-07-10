import cv2
import numpy as np
from keras.models import load_model

THICKNESS = 2
model = load_model('my_mnist.h5')


def draw_rectangle(frame, start_point, end_point):
    color = (0, 0, 255)
    return cv2.rectangle(frame, start_point, end_point, color, THICKNESS)


def process(frame, start_point, end_point):
    color = (0, 0, 255)
    rect_start = draw_rectangle(frame, start_point, end_point)
    rect_end = draw_rectangle(frame, [20, 20], [225, 70])
    hsv_frame = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

    s_h, v_h, s_l, v_l = 255, 255, 100, 100

    color_ranges = [
        ("red1", [170, s_l, v_l], [180, s_h, v_h]),
        ("red2", [0, s_l, v_l], [10, s_h, v_h]),
        ("green", [45, s_l, v_l], [70, s_h, v_h]),
        ("blue", [105, s_l, v_l], [125, s_h, v_h]),
        ("yellow", [20, s_l, v_l], [40, s_h, v_h]),
        ("white", [0, 0, 150], [180, 255, 255])
    ]
    mask_frame = hsv_frame[start_point[1]:end_point[1] + 1, start_point[0]:end_point[0] + 1]
    color_rates = {}

    for name, lower, upper in color_ranges:
        mask = cv2.inRange(mask_frame, np.array(lower), np.array(upper))
        color_rates[name] = np.count_nonzero(mask) / ((end_point[0] - start_point[0]) * (end_point[1] - start_point[1]))

    cropped_frame = frame[start_point[1]:end_point[1], start_point[0]:end_point[0]]
    gray = cv2.cvtColor(cropped_frame, cv2.COLOR_BGR2GRAY)
    resized = cv2.resize(gray, (28, 28), interpolation=cv2.INTER_AREA)
    resized = cv2.bitwise_not(resized)
    input_data = np.reshape(resized, (1, 28, 28, 1)).astype('float32') / 255
    prediction = model.predict(input_data)
    predicted_class = np.argmax(prediction)

    # cv2.putText(frame, str(predicted_class), (50, 100), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2, cv2.LINE_AA)
    org = end_point
    font = cv2.FONT_HERSHEY_SIMPLEX
    fontScale = 0.7
    cv2.putText(rect_end,
                        f" {str(round(color_rates['red1'] + color_rates['red2']) * 230).ljust(3, '0')}"
                        f" {str(round(color_rates['green'] * 255)).ljust(3, '0')}"
                        f" {str(round(color_rates['blue'] * 255)).ljust(3, '0')}",
                        [21, 50], font, 0.8,
                        [color_rates['blue'] * 255, color_rates['green'] * 255,
                         (color_rates['red1'] + color_rates['red2']) * 230
                         ], THICKNESS, cv2.LINE_AA)

    dominant_color = max({k: v for k, v in color_rates.items() if k in ('green', 'blue', 'yellow')}, key=color_rates.get)
    print(color_rates)
    if color_rates[dominant_color] > 0:
        cv2.putText(rect_start, f" {predicted_class} ({dominant_color}) ", org, font, fontScale, color, THICKNESS, cv2.LINE_AA)
    else:
        cv2.putText(rect_start, f' {predicted_class} ', org, font, fontScale, color, THICKNESS, cv2.LINE_AA)

    return rect_start, resized


def mouse_callback(event, x, y, flags, param):
    global start_point, end_point, dragging, resizing

    if event == cv2.EVENT_LBUTTONDOWN:
        dragging = True
        start_point = (x, y)
        end_point = (x + 1, y + 1)

    elif event == cv2.EVENT_MOUSEMOVE and dragging:
        end_point = (x + 1, y + 1)
    elif event == cv2.EVENT_LBUTTONUP:
        dragging = False
        end_point = (x + 1, y + 1)

    if end_point[0] <= start_point[0] + 100:
        end_point = (start_point[0] + 100, end_point[1])
    if end_point[1] <= start_point[1] + 100:
        end_point = (end_point[0], start_point[1] + 100)

# Open Default Camera
cap = cv2.VideoCapture(0)
rect_size = 100
cv2.namedWindow('Digits & Color CV')
cv2.namedWindow('processed')
cv2.setMouseCallback('Digits & Color CV', mouse_callback)
dragging = False
resizing = False

width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
start_point = (int(height / 2 - rect_size / 2), int(width / 2 - rect_size / 2))
end_point = (int(height / 2 + rect_size / 2), int(width / 2 + rect_size / 2))

while cap.isOpened():
    # Take each Frame
    ret, frame = cap.read()

    processed, resized = process(frame, start_point, end_point)

    cv2.imshow('Digits & Color CV', processed)
    cv2.imshow('processed', resized)

    # Move the rectangle based on arrow keys
    k = cv2.waitKeyEx(1)
    if k == 52:
        break   # exit
    elif k == 2424832:  # left arrow key
        start_point = (max(start_point[0] - 5, 0), start_point[1])
        end_point = (max(end_point[0] - 5, rect_size), end_point[1])
    elif k == 2490368:  # up arrow key
        start_point = (start_point[0], max(start_point[1] - 5, 0))
        end_point = (end_point[0], max(end_point[1] - 5, rect_size))
    elif k == 2555904:  # right arrow key
        start_point = (min(start_point[0] + 5, height - rect_size), start_point[1])
        end_point = (min(end_point[0] + 5, height), end_point[1])
    elif k == 2621440:  # down arrow key
        start_point = (start_point[0], min(start_point[1] + 5, width - rect_size))
        end_point = (end_point[0], min(end_point[1] + 5, width))

# Release the camera and close all windows
cap.release()
cv2.destroyAllWindows()