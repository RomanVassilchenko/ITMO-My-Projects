#include "main.h"
#include "pca9538.h"
#include "kb.h"
#include "sdk_uart.h"
#include "usart.h"

#define KBRD_ADDR 0xE2

HAL_StatusTypeDef Set_Keyboard( void ) {
	HAL_StatusTypeDef ret = HAL_OK;
	uint8_t buf;

	buf = 0;
	ret = PCA9538_Write_Register(KBRD_ADDR, POLARITY_INVERSION, &buf);
	if( ret != HAL_OK ) {
		UART_Transmit((uint8_t*)"Error write polarity\n");
		goto exit;
	}

	buf = 0;
	ret = PCA9538_Write_Register(KBRD_ADDR, OUTPUT_PORT, &buf);
	if( ret != HAL_OK ) {
		UART_Transmit((uint8_t*)"Error write output\n");
	}

exit:
	return ret;
}

uint8_t Check_Row( uint8_t  Nrow ) {
	uint8_t Nkey = 0x00;
	HAL_StatusTypeDef ret = HAL_OK;
	uint8_t buf;
	uint8_t kbd_in;

	ret = Set_Keyboard();
	if( ret != HAL_OK ) {
		UART_Transmit((uint8_t*)"Error write init\n");
	}

	buf = Nrow;
	ret = PCA9538_Write_Register(KBRD_ADDR, CONFIG, &buf);
	if( ret != HAL_OK ) {
		UART_Transmit((uint8_t*)"Error write config\n");
	}

	ret = PCA9538_Read_Inputs(KBRD_ADDR, &buf);
	if( ret != HAL_OK ) {
		UART_Transmit((uint8_t*)"Read error\n");
	}

	kbd_in = buf & 0x70;
	Nkey = kbd_in;
	if( kbd_in != 0x70) {
		if( !(kbd_in & 0x10) ) {
			switch (Nrow) {
				case ROW1:
					Nkey = 0x01;
					break;
				case ROW2:
					Nkey = 0x04;
					break;
				case ROW3:
					Nkey = 0x07;
					break;
				case ROW4:
					Nkey = 0x0A;
					break;
			}
		}
		if( !(kbd_in & 0x20) ) {
			switch (Nrow) {
				case ROW1:
					Nkey = 0x02;
					break;
				case ROW2:
					Nkey = 0x05;
					break;
				case ROW3:
					Nkey = 0x08;
					break;
				case ROW4:
					Nkey = 0xB;
					break;
			}
		}
		if( !(kbd_in & 0x40) ) {
			switch (Nrow) {
				case ROW1:
					Nkey = 0x03;
					break;
				case ROW2:
					Nkey = 0x06;
					break;
				case ROW3:
					Nkey = 0x09;
					break;
				case ROW4:
					Nkey = 0xC;
					break;
			}
		}
	}
	else Nkey = 0x00;

	return Nkey;
}
