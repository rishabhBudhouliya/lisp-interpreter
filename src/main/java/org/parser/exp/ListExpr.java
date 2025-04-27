package org.parser.exp;

import java.util.List;

public class ListExpr implements Expr {
    public final List<Expr> elements;

    public ListExpr(List<Expr> elements) {
        this.elements = elements;
    }

    public String toString() {
        return elements.toString();
    }
}
