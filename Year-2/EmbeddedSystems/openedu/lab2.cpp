#include "hal.h"
#include <bitset>

const int delay = 100;
int frameIndex = 0;
int ledPins[] = {GPIO_PIN_3, GPIO_PIN_4, GPIO_PIN_5, GPIO_PIN_6, GPIO_PIN_8, GPIO_PIN_9, GPIO_PIN_11, GPIO_PIN_12};
unsigned int swPins[4] = {GPIO_PIN_12, GPIO_PIN_10, GPIO_PIN_8, GPIO_PIN_4};

int frames[7][8] = {
        {1,0,0,0,1,0,0,0},
        {0,1,0,0,0,1,0,0},
        {0,0,1,0,0,0,1,0},
        {0,0,0,1,0,0,0,1},
        {0,0,1,0,0,0,1,0},
        {0,1,0,0,0,1,0,0},
        {1,0,0,0,1,0,0,0}
};

std::bitset<4> swStatus;

void readAndSetSwStatus(){
    for(int i = 0; i < 4; i++) {
        GPIO_PinState state = HAL_GPIO_ReadPin(GPIOE, swPins[i]);
        swStatus[i] = state == GPIO_PIN_SET;
    }
}

void setLeds(int num[8]){
    for (int i = 0; i < 8; i++){
        if (num[i] == 1){
            HAL_GPIO_WritePin(GPIOD, ledPins[i], GPIO_PIN_SET);
        }
    }
}

void unsetLeds(int num[8]){
    for (int i = 0; i < 8; i++){
        if (num[i] == 1){
            HAL_GPIO_WritePin(GPIOD, ledPins[i], GPIO_PIN_RESET);
        }
    }
}

void TIM6_IRQ_Handler(){
    setLeds(frames[frameIndex]);
}

void TIM7_IRQ_Handler(){
    unsetLeds(frames[frameIndex]);
    frameIndex++;
    if(frameIndex == 7){
        frameIndex = 0;
    }
    readAndSetSwStatus();
    WRITE_REG(TIM6_ARR, 500 + swStatus.to_ulong() * delay);
    WRITE_REG(TIM7_ARR, 500 + swStatus.to_ulong() * delay);
}

int umain(){
    frameIndex = 0;
    registerTIM6_IRQHandler(TIM6_IRQ_Handler);
    registerTIM7_IRQHandler(TIM7_IRQ_Handler);

    WRITE_REG(TIM6_ARR, 500);
    WRITE_REG(TIM6_DIER, TIM_DIER_UIE);
    WRITE_REG(TIM6_PSC, 0);

    WRITE_REG(TIM7_ARR, 500);
    WRITE_REG(TIM7_DIER, TIM_DIER_UIE);
    WRITE_REG(TIM7_PSC, 1);

    __enable_irq();

    WRITE_REG(TIM6_CR1, TIM_CR1_CEN);
    WRITE_REG(TIM7_CR1, TIM_CR1_CEN);

    return 0;
}
