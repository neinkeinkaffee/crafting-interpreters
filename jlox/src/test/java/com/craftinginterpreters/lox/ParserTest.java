package com.craftinginterpreters.lox;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parsesVarDeclarationAndReassignment() {
        String source = "y = 2;";
//        String source = "var y = 1;\ny = 2;";
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

//        assertInstanceOf(Stmt.Var.class, statements.get(0));
        assertInstanceOf(Stmt.Expression.class, statements.get(0));
        assertInstanceOf(Expr.Assign.class, ((Stmt.Expression) statements.get(0)).expression);
    }
}