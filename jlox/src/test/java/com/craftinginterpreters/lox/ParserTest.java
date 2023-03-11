package com.craftinginterpreters.lox;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parsesVarDeclarationAndReassignment() {
        String source = """
            var y = 1;
            y = 2;
        """;
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        assertInstanceOf(Stmt.Var.class, statements.get(0));
        assertInstanceOf(Stmt.Expression.class, statements.get(1));
        assertInstanceOf(Expr.Assign.class, ((Stmt.Expression) statements.get(1)).expression);
    }

    @Test
    void parsesNestedBlocks() {
        String source = """
            var a = "global a";
            var b = "global b";
            var c = "global c";
            {
              var a = "outer a";
              var b = "outer b";
              {
                var a = "inner a";
                print a;
                print b;
                print c;
              }
              print a;
              print b;
              print c;
            }
            print a;
            print b;
            print c;
        """;
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        assertInstanceOf(Stmt.Var.class, statements.get(0));
        assertInstanceOf(Stmt.Var.class, statements.get(1));
        assertInstanceOf(Stmt.Var.class, statements.get(2));
        assertInstanceOf(Stmt.Block.class, statements.get(3));
        assertInstanceOf(Stmt.Print.class, statements.get(4));
        assertInstanceOf(Stmt.Print.class, statements.get(5));
        assertInstanceOf(Stmt.Print.class, statements.get(6));
        List<Stmt> nestedStatements = ((Stmt.Block) statements.get(3)).statements;
        assertInstanceOf(Stmt.Var.class, nestedStatements.get(0));
        assertInstanceOf(Stmt.Var.class, nestedStatements.get(1));
        assertInstanceOf(Stmt.Block.class, nestedStatements.get(2));
        List<Stmt> doublyNestedStatements = ((Stmt.Block) nestedStatements.get(2)).statements;
        assertInstanceOf(Stmt.Var.class, doublyNestedStatements.get(0));
        assertInstanceOf(Stmt.Print.class, doublyNestedStatements.get(1));
        assertInstanceOf(Stmt.Print.class, doublyNestedStatements.get(2));
        assertInstanceOf(Stmt.Print.class, doublyNestedStatements.get(3));
        assertInstanceOf(Stmt.Print.class, statements.get(4));
        assertInstanceOf(Stmt.Print.class, statements.get(5));
        assertInstanceOf(Stmt.Print.class, statements.get(6));
    }

    @Test
    void parsesifElse() {
        String source = """
            if (true) print "hello"; else print "not hello";
        """;
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        assertInstanceOf(Stmt.If.class, statements.get(0));
    }

    @Test
    void parsesIfElseWithBlocks() {
        String source = """
            if (true) {
                print "hello"
            }; else {
                print "not hello";
            }
        """;
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        assertInstanceOf(Stmt.If.class, statements.get(0));
        assertInstanceOf(Stmt.Block.class, ((Stmt.If)statements.get(0)).thenBranch);
    }
}