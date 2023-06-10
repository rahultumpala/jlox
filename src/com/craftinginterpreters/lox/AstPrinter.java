package com.craftinginterpreters.lox;

public class AstPrinter implements Expr.Visitor<String>{
    String print(Expr expr){
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if(expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    private String parenthesize(String name, Expr... expressions){
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(name);
        for(Expr expr: expressions){
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }
}
/*
Metasyntax
+ = 1 or more
? = 0 or 1
* = 0 or more

Condensed form (using metasyntax)
----
expr → expr ( "(" ( expr ( "," expr )* )? ")" | "." IDENTIFIER )+
     | IDENTIFIER
     | NUMBER
----

Expanded form
----
ident     -> "." IDENTIFIER ident
ident     -> "." IDENTIFIER

args      -> "," expr args
args      -> "," expr

group     -> "(" expr multipleArgs ")"
group     -> "(" expr ")"
group     -> "(" ")"

next      -> group next
next      -> ident next
next      -> group
next      -> ident

expr      -> expr next
expr      -> IDENTIFIER
expr      -> NUMBER
----

Did I get this right?
 */

