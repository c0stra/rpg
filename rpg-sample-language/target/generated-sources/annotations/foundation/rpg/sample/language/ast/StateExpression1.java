package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateExpression1 extends StackState<foundation.rpg.sample.language.ast.Expression, State> {

// NoStack:
// Stack:
    public StateExpression1(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.sample.language.ast.Expression node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitSemicolon(foundation.rpg.common.symbols.Semicolon symbol) {
        return new StateSemicolon1(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        State stack1 = this.getPrev();
        return Arrays.asList(this.getNode());
    }

}
