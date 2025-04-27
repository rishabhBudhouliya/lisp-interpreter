package org.parser.exp;

public class Atom implements Expr {
    public final Object value;

    public Atom(String token) {
        this.value = parse(token);
    }

    private Object parse(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException ex) {
            try {
                return Double.parseDouble(token);
            } catch (NumberFormatException ex2) {
                return token;
            }
        }
    }

    public String toString() {
        return value.toString();
    }
}
