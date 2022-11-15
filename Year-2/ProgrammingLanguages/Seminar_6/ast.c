/* ast.c */

#include <stdlib.h>

#include "ast.h"

struct AST *newnode(struct AST ast) {
  struct AST *const node = malloc(sizeof(struct AST));
  *node = ast;
  return node;
}

struct AST _lit(int64_t value) {
  return (struct AST){AST_LIT, .as_literal = {value}};
}

struct AST *lit(int64_t value) {
  return newnode(_lit(value));
}
struct AST _unop(enum unop_type type, struct AST *operand) {
  return (struct AST){AST_UNOP, .as_unop = {type, operand}};
}

struct AST *unop(enum unop_type type, struct AST *operand) {
  return newnode(_unop(type, operand));
}

struct AST _binop(enum binop_type type, struct AST *left, struct AST *right) {
  return (struct AST){AST_BINOP, .as_binop = {type, left, right}};
}
struct AST *binop(enum binop_type type, struct AST *left, struct AST *right) {
  return newnode(_binop(type, left, right));
}

static const char *BINOPS[] = {
    [BIN_PLUS] = "+", [BIN_MINUS] = "-", [BIN_MUL] = "*", [BIN_DIV] = "/" };
static const char *UNOPS[] = {[UN_NEG] = "-"};

typedef void(printer)(FILE *, struct AST *);


static void print_binop(FILE *f, struct AST *ast) {
  fprintf(f, "(");
  print_ast(f, ast->as_binop.left);
  fprintf(f, ")");
  fprintf(f, "%s", BINOPS[ast->as_binop.type]);
  fprintf(f, "(");
  print_ast(f, ast->as_binop.right);
  fprintf(f, ")");
}
static void print_unop(FILE *f, struct AST *ast) {
  fprintf(f, "%s(", UNOPS[ast->as_unop.type]);
  print_ast(f, ast->as_unop.operand);
  fprintf(f, ")");
}
static void print_lit(FILE *f, struct AST *ast) {
  fprintf(f, "%" PRId64, ast->as_literal.value);
}

static printer *ast_printers[] = {
    [AST_BINOP] = print_binop, [AST_UNOP] = print_unop, [AST_LIT] = print_lit };

void print_ast(FILE *f, struct AST *ast) {
  if (ast)
    ast_printers[ast->type](f, ast);
  else
    fprintf(f, "<NULL>");
}

int64_t calc_ast(struct AST *ast) {
  /* TODO */
  return 0;
}

void p_print_ast(FILE *f, struct AST *ast) {
  /* TODO */
}
