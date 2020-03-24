package foundation.fluent.jast.sample.language.ast;

import foundation.fluent.jast.StartSymbol;
import foundation.fluent.jast.common.*;

import java.util.ArrayList;
import java.util.List;

public class AstFactory {

    @StartSymbol
    public static Program program(List<Statement> statements, End end) {
        return new Program(statements);
    }

    public static List<Statement> statements() {
        return new ArrayList<>();
    }

    public static List<Statement> statements(List<Statement> statements, Statement statement) {
        statements.add(statement);
        return statements;
    }

    public static Statement statement(Expression expression, Dot dot) {
        return new ExpressionStatement(expression);
    }

    public static Expression expression(Operand operand) {
        return operand.getExpression();
    }

    public static Expression expression(Expression left, Plus plus, Operand right) {
        return new BinaryExpression(left, right.getExpression());
    }

    public static Operand operand(Identifier identifier) {
        return new Operand(identifier);
    }

    public static Operand expression(LPar l, Expression expression, RPar r) {
        return new Operand(expression);
    }

    public void ignore(WhiteSpace w) {}

    public void ignore(Comment c) {}

}