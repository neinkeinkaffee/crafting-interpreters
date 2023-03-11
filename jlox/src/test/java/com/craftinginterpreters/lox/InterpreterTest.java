package com.craftinginterpreters.lox;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {
    @Test
    void interpretsFunctionWithReturnValue() {
        Interpreter interpreter = new Interpreter();
        String source = """
            fun fib(n) {
                      if (n <= 1) return n;
                      return fib(n - 2) + fib(n - 1);
                    }
                    
                    for (var i = 0; i < 20; i = i + 1) {
                      print fib(i);
                    }
        """;
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        interpreter.interpret(statements);
    }
}