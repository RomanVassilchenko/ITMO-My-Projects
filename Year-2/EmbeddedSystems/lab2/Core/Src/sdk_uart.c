//===================================================================//
//								USART6								 //
//===================================================================//
#include "sdk_uart.h"
#include "usart.h"
#include <string.h>


void UART_Transmit(uint8_t data[]){
	HAL_UART_Transmit(&huart6, data, strlen((const char*)data), 1000);
}

HAL_StatusTypeDef UART_Receive(uint8_t *data, uint8_t len){
	HAL_StatusTypeDef st = HAL_ERROR;
	st = HAL_UART_Receive(&huart6, data, len, 0xFFFF);
	return st;
}
