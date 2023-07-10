#include "main.h"
#include "i2c.h"
#include "sdk_uart.h"
#include "pca9538.h"

HAL_StatusTypeDef PCA9538_Read_Register(uint16_t addr, pca9538_regs_t reg, uint8_t* buf) {
	return HAL_I2C_Mem_Read(&hi2c1, addr | 1, reg, 1, buf, 1, 100);
}


HAL_StatusTypeDef PCA9538_Write_Register(uint16_t addr, pca9538_regs_t reg, uint8_t* buf) {
	return HAL_I2C_Mem_Write(&hi2c1, addr & 0xFFFE, reg, 1, buf, 1, 100);
}

HAL_StatusTypeDef PCA9538_Read_Config(uint16_t addr, uint8_t* buf) {
	HAL_StatusTypeDef ret = HAL_OK;
	uint8_t *buf_ptr = buf;
	uint8_t i;

	for( i=0; i<4; i++ ) {
		ret = PCA9538_Read_Register(addr, i, buf_ptr++);

		if(ret!=HAL_OK)
			return ret;
	}

	return ret;
}

HAL_StatusTypeDef PCA9538_Check_DefaultConfig(uint16_t addr) {
	HAL_StatusTypeDef ret = HAL_OK;
	uint8_t buf[4];

	ret = PCA9538_Read_Config(addr, buf);
	if( ret != HAL_OK ){
		UART_Transmit((uint8_t*)"Can't read default config\n");
	}
	else if ( buf[1] != 0xFF && buf[2] != 0xFF && buf[3] != 0xFF ) {
		UART_Transmit((uint8_t*)"Non-reset config\n");
		UART_Transmit(buf);
		ret = HAL_ERROR;
	}

	return ret;
}

HAL_StatusTypeDef PCA9538_Read_Inputs(uint16_t addr, uint8_t* buf) {
	return PCA9538_Read_Register(addr, INPUT_PORT, buf);
}


