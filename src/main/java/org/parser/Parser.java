package org.parser;

import org.parser.exp.Atom;
import org.parser.exp.Expr;
import org.parser.exp.ListExpr;
import org.parser.exp.Token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


/**
 * Parser implements a recursive descent parser for Lisp
 *  - offers a static function parse()
 *  - contains a ParseException
 */
public class Parser {

    /**
     * Parse a given input string into a lisp expression
     * @param input a string
     * @return Expr
     */
    public static Expr parse(String input) {
        Deque<Token> tokens = tokenize(input);
        Expr result = readFromTokens(tokens);
        if (!tokens.isEmpty()) {
            throw new ParseException("Unexpected EOF token",
                    tokens.peekFirst().getLine(), tokens.peekFirst().getColumn());
        }
        return result;
    }

    /**
     * Convert given input string into discrete tokens
     * @param input a string
     * @return a left-to-right ordered deque of tokens
     */
    private static Deque<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int line = 1, col = 0;
        for (int i = 0; i<input.length(); i++) {
            char c = input.charAt(i);
            col++;
            if (c == '\n') {
                line++;
                col = 0;
                continue;
            }
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c == '(' || c == ')') {
                tokens.add(new Token(String.valueOf(c), line, col));
            } else {
                int startCol = col;
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                while (peak(i, input)) {
                    sb.append(input.charAt(++i));
                    col++;
                }
                tokens.add(new Token(sb.toString(), line, startCol));
            }
        }
        return new ArrayDeque<>(tokens);
    }

    /**
     * a top-down recursive walk of the given ordered sequence of tokens
     * @param tokens a left-to-right ordered deque of tokens
     * @return abstract syntax tree
     */
    private static Expr readFromTokens(Deque<Token> tokens) {
        if (tokens.isEmpty()) {
            throw new RuntimeException("Empty input string");
        }
        Token token = tokens.removeFirst();
        switch (token.getLexeme()) {
            case "(" -> {
                List<Expr> list = new ArrayList<>();
                while(!tokens.isEmpty() && !")".equals(tokens.peekFirst().getLexeme())) {
                    list.add(readFromTokens(tokens));
                }
                if (tokens.isEmpty())
                    throw new ParseException("Unmatched '('", token.getLine(), token.getColumn());
                tokens.removeFirst();
                return new ListExpr(list);
            }
            case ")" -> throw new ParseException("Unexpected syntax ')'", token.getLine(), token.getColumn());
            default -> {
                return new Atom(token.getLexeme());
            }
        }
    }

    private static boolean peak(int currentIndex, String input) {
        int nextIndex = currentIndex + 1;
        return nextIndex < input.length() && !Character.isWhitespace(input.charAt(nextIndex)) &&
                input.charAt(nextIndex) != '(' && input.charAt(nextIndex) != ')';
    }
}

class ParseException extends RuntimeException {
    public ParseException(String message, int line, int column) {
        super("Line " + line + ", col " + column + ": " + message);
    }
}