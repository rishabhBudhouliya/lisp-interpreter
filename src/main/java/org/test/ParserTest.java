package org.test;

import org.parser.Parser;
import org.parser.exp.Expr;

import java.text.ParseException;

public class ParserTest {

    public static void main(String[] args) {
        testSanityInput();
        testIncorrectInput();
        testAmbiguityInput();
    }

    public static void testSanityInput() {
        System.out.println("TEST 1");
        String test = "(first (list 1 (+ 2 3) 9))";
        try {
            Expr result = Parser.parse(test);
            System.out.printf("Test succeeded with parsing with Expr: %s", result);
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(ParseException.class.getSimpleName())) {
                System.out.printf("Test failed with error message: %s", e.getMessage());
            }
        }
    }

    public static void testIncorrectInput() {
        System.out.println("\nTEST 3");
        String test = "(+ 1 2";
        try {
            Parser.parse(test);
            System.out.println("Test failed to throw an error");
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(ParseException.class.getSimpleName())) {
                System.out.printf("Test succeeded with error message: %s", e.getMessage());
            }
        }
    }

    public static void testAmbiguityInput() {
        System.out.println("\nTEST 2");
        String test = "(!= 1 1)";
        try {
            Expr result = Parser.parse(test);
            System.out.printf("Test succeeded with parsing with Expr: %s", result);
        } catch (Exception e) {
            if (e.getClass().getSimpleName().equals(ParseException.class.getSimpleName())) {
                System.out.printf("Test failed with error message: %s", e.getMessage());
            }
        }
    }
}
