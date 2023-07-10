#include "hal.h"
#include <iostream>

int ledPins[] = {GPIO_PIN_3, GPIO_PIN_4, GPIO_PIN_5, GPIO_PIN_6, GPIO_PIN_8, GPIO_PIN_9, GPIO_PIN_11, GPIO_PIN_12};
unsigned int swPins[] = {GPIO_PIN_4, GPIO_PIN_8, GPIO_PIN_10, GPIO_PIN_12};

void turnOnGreenLed()
{
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_SET);
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
}

void turnOnYellowLed()
{
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_SET);
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
}

void turnOnRedLed()
{
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
    HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_SET);
}

void pauseAnimation()
{
    turnOnRedLed();
    HAL_Delay(200);
    GPIO_PinState state = GPIO_PIN_SET;
    while (state == GPIO_PIN_SET)
        state = HAL_GPIO_ReadPin(GPIOC, GPIO_PIN_15);
    turnOnGreenLed();
}

void clearLeds()
{
    for (int i = 0; i < 8; i++)
    {
        HAL_GPIO_WritePin(GPIOD, ledPins[i], GPIO_PIN_RESET);
    }
}

void checkButton()
{
    if (HAL_GPIO_ReadPin(GPIOC, GPIO_PIN_15) == GPIO_PIN_RESET)
    {
        pauseAnimation();
    }
}

bool isAnimationCode()
{
    return (HAL_GPIO_ReadPin(GPIOE, GPIO_PIN_12) == GPIO_PIN_RESET &&
            HAL_GPIO_ReadPin(GPIOE, GPIO_PIN_10) == GPIO_PIN_SET &&
            HAL_GPIO_ReadPin(GPIOE, GPIO_PIN_8) == GPIO_PIN_SET &&
            HAL_GPIO_ReadPin(GPIOE, GPIO_PIN_4) == GPIO_PIN_RESET);
}

void startAnimationVariant6()
{
    clearLeds();
    static const uint8_t frames[][8] = {
        {0, 0, 0, 1, 1, 0, 0, 0},
        {0, 0, 1, 1, 1, 1, 0, 0},
        {0, 1, 1, 1, 1, 1, 1, 0},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {0, 1, 1, 1, 1, 1, 1, 0},
        {0, 0, 1, 1, 1, 1, 0, 0},
        {0, 0, 0, 1, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0}};

    int numFrames = sizeof(frames) / sizeof(frames[0]);

    for (int i = 0; i < numFrames; i++)
    {
        checkButton();
        for (int j = 0; j < 8; j++)
        {
            HAL_GPIO_WritePin(GPIOD, ledPins[j], frames[i][j] ? GPIO_PIN_SET : GPIO_PIN_RESET);
        }
        HAL_Delay(500);
        checkButton();

        if (!isAnimationCode())
            break;
    }
}

void showSwitchStates()
{
    for (int i = 0; i < 4; i++)
    {
        GPIO_PinState state = HAL_GPIO_ReadPin(GPIOE, swPins[i]);
        HAL_GPIO_WritePin(GPIOD, ledPins[i], state);
    }
    for(int i = 4; i < 8; i++) {
        HAL_GPIO_WritePin(GPIOD, ledPins[i], GPIO_PIN_RESET);
    }
}

int umain()
{
    while (true)
    {
        if (isAnimationCode())
        {
            turnOnGreenLed();
            startAnimationVariant6();
        }
        else
        {
            showSwitchStates();
            turnOnYellowLed();
        }
    }
    return 0;
}
