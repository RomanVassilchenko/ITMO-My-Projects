
#include "stm32f4xx_hal.h"

#ifndef Fonts
#define Fonts

typedef struct {
	const uint8_t FontWidth;
	uint8_t FontHeight;
	const uint16_t *data;
} FontDef;

extern FontDef Font_7x10;
extern FontDef Font_11x18;
extern FontDef Font_16x26;

#endif
