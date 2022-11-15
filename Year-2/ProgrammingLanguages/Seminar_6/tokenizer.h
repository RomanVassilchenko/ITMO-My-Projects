/* tokenizer.h */

#pragma once
#ifndef _LLP_TOKENIZER_H_
#define _LLP_TOKENIZER_H_

#include <inttypes.h>

#include "ring.h"

struct token {
  enum token_type { 
    TOK_PLUS = 0, TOK_MINUS, TOK_MUL, 
    TOK_DIV, TOK_OPEN, TOK_CLOSE, TOK_LIT, 
    TOK_NEG, TOK_END, TOK_ERROR } type;
  int64_t value;
};

DECLARE_RING(token, struct token)

struct ring_token *tokenize(char *str);

extern const char *TOKENS_STR[];

#endif
