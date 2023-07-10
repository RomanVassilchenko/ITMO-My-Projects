
# coding: utf-8

import glob
import os
import xml.etree.ElementTree as ET  # XML документ в виде дерева
import numpy as np  # Пакет для научных вычислений
from urllib.request import urlopen  # открытие ссылок

# https://www.meteoservice.ru/content/export - сайт с погодой

# открытие URL с XML погодой
filedata = urlopen('https://xml.meteoservice.ru/export/gismeteo/point/69.xml')
datatowrite = filedata.read()

with open('C:/Users/Public/Documents/69.xml', 'wb') as f:  # сохранение в папку
    f.write(datatowrite)


class color:
    BOLD = '\033[1m'
    END = '\033[0m'

# информация местоположения


def town(t):
    index = int(t.get('index'))
    if index == 69:
        print("{}Санкт-Петербург{}".format(color.BOLD, color.END), end=' | ')

    if index == 1:
        print("{}Алматы{}".format(color.BOLD, color.END), end=' | ')

# функция дней недели


def day_of_the_week(forecast):
    day = int(forecast.get('weekday'))
    if day == 1:
        print(f"Дата: {forecast.get('day')}.{forecast.get('month')}.{forecast.get('year')} |"
              f" Время: {forecast.get('hour')}:00 | Воскресенье")
    elif day == 2:
        print(f"Дата: {forecast.get('day')}.{forecast.get('month')}.{forecast.get('year')} |"
              f" Время: {forecast.get('hour')}:00 | Понедельник")
    elif day == 3:
        print(f"Дата: {forecast.get('day')}.{forecast.get('month')}.{forecast.get('year')} |"
              f" Время: {forecast.get('hour')}:00 | Вторник")
    elif day == 4:
        print(f"Дата: {forecast.get('day')}.{forecast.get('month')}.{forecast.get('year')} |"
              f" Время: {forecast.get('hour')}:00 | Среда")
    elif day == 5:
        print(f"Дата: {forecast.get('day')}.{forecast.get('month')}.{forecast.get('year')} |"
              f" Время: {forecast.get('hour')}:00 | Четверг")
    elif day == 6:
        print(f"Дата: {forecast.get('day')}.{forecast.get('month')}.{forecast.get('year')} |"
              f" Время: {forecast.get('hour')}:00 | Пятница")
    else:
        print(f"Дата: {forecast.get('day')}.{forecast.get('month')}.{forecast.get('year')} |"
              f" Время: {forecast.get('hour')}:00 | Суббота")

# Атмосферные явления


def phenomena(forecast_ch):

    # облачность по градациям
    cloudiness = int(forecast_ch.get('cloudiness'))
    if cloudiness == -1:
        print(f"Туман", end=' | ')
    elif cloudiness == 0:
        print(f"Ясно", end=' | ')
    elif cloudiness == 1:
        print(f"Малооблачно", end=' | ')
    elif cloudiness == 2:
        print(f"Облачно", end=' | ')
    else:
        print("Пасмурно", end=' | ')

    # тип осадков
    prec = int(forecast_ch.get('precipitation'))
    #rpower = int(forecast_ch.get('rpower'))
    #spower = int(forecast_ch.get('spower'))

    if prec == 3:
        print("Смешанные осадки")
    elif prec == 4:
        print("Дождь")
    elif prec == 5:
        print("Ливень")
    elif prec == 6 or prec == 7:
        print("Снег")
    elif prec == 8:
        print("Гроза")
    elif prec == 9:
        print("Нет данных")
    else:
        print("Без осадков")

# Информация по ветру


def wind(forecast_ch):

    direction = int(forecast_ch.get('direction'))
    print(
        f"Скорость ветра:\n\tMin: {f.get('min')} м/c, Max: {f.get('max')} м/с")

    if direction == 0:
        print(f"Направление ветра: северный")
    elif direction == 1:
        print(f"Направление ветра: северно-восточный")
    elif direction == 2:
        print(f"Направление ветра: восточный")
    elif direction == 3:
        print(f"Направление ветра: юго-восточный")
    elif direction == 4:
        print(f"Направление ветра: южный")
    elif direction == 5:
        print(f"Направление ветра: юго-западный")
    elif direction == 6:
        print(f"Направление ветра: западный")
    else:
        print("Направление ветра: северо-западный")


# Получение XML в виде дерева
tree = ET.parse('C:/Users/Public/Documents/69.xml')
root = tree.getroot()  # Получение корневого элемента
# Проход по всем потомкам корневого элемента
for child in root:
    for ch in iter(child):
        if ch.tag == 'TOWN':
            town(ch)
        for forecast in iter(ch):
            if forecast.tag == 'FORECAST':
                day_of_the_week(forecast)
            for f in iter(forecast):
                if f.tag == 'TEMPERATURE':
                    print(
                        f"Температура:\n\tMin: {f.get('min')}°C, Max: {f.get('max')}°C")
                if f.tag == 'PHENOMENA':
                    phenomena(f)
                if f.tag == 'WIND':
                    wind(f)
                if f.tag == 'RELWET':
                    print(
                        f"Влажность:\n\tMin: {f.get('min')}%, Max: {f.get('max')}%")
                if f.tag == 'HEAT':
                    print(f"Ощущается: {f.get('max')}°C\n")
                if f.tag == 'PRESSURE':
                    print(
                        f"Атмосферное давление:\n\tMin: {f.get('min')} мм.рт.ст, Max: {f.get('max')}мм.рт.ст")
