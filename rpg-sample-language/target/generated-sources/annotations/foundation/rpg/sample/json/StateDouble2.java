package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateDouble2 extends StackState<java.lang.Double, State> {

// NoStack:
// Stack:
    public StateDouble2(foundation.rpg.sample.json.JsonFactory factory, java.lang.Double node, State prev) {
        super(factory, node, prev);
    }


// Reduce:
    @Override
    public State visitRBr(foundation.rpg.common.symbols.RBr symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitObject(getFactory().is(this.getNode())).visitRBr(symbol);
    }

    @Override
    public State visitComma(foundation.rpg.common.symbols.Comma symbol) throws UnexpectedInputException {
        State stack1 = this.getPrev();
        return stack1.visitObject(getFactory().is(this.getNode())).visitComma(symbol);
    }


// Shift:
// Accept:
}
