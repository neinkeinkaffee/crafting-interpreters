package com.craftinginterpreters.lox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ASTPrinterTest {
    @Test
    void printsAST() {
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)
                ),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(
                        new Expr.Literal(45.67)
                )
        );

        assertEquals("(* (- 123) (group 45.67))", new ASTPrinter().print(expression));
    }
}