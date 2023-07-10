#ifndef SDK_UART_H_
#define SDK_UART_H_

#include "main.h"

void UART_Transmit(uint8_t data[]);
HAL_StatusTypeDef UART_Receive(uint8_t *data, uint8_t len);

#endif /* SDK_UART_H_ */
