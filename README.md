# Simple list parser

This project implements the parsing stage for a simple Lisp-like language, designed as the foundation for an interpreter. It takes a string containing S-expression code and converts it into an Abstract Syntax Tree (AST).

## Problem
Write code that takes some Lisp code and returns an abstract syntax tree. The AST should represent the structure of the code and the meaning of each token. For example, if your code is given "(first (list 1 (+ 2 3) 9))", it could return a nested array like ["first", ["list", 1, ["+", 2, 3], 9]].

During your interview, you will pair on writing an interpreter to run the AST. You can start by implementing a single built-in function (for example, +) and add more if you have time.

## Features
This project contains a parser that can
- **Tokenizer**: tokenize a given input string into lexemes
- **Recursive Descent Parser**: convert the ordered sequence of tokens into an abstract 
syntax tree
- **Unit tests**: to cover sanity and two edge cases

## Usage
```java
import org.parser.Expr;
import org.parser.Parser;
import org.parser.ParseException;

public class Main {
    public static void main(String[] args) {
        String lispCode = "(first (list 1 (+ 2 3) 9))";
        try {
            Expr ast = Parser.parse(lispCode);
            System.out.println("Successfully parsed. AST structure:");
            System.out.println(ast); // Requires toString implementation
        } catch (ParseException e) {
            System.err.println("Parsing failed: " + e.getMessage());
        }

        String badCode = "(list 1 (";
         try {
            Expr ast = Parser.parse(badCode);
        } catch (ParseException e) {
            System.err.println("Unexpected syntax: " + e.getMessage());
        }
    }
}