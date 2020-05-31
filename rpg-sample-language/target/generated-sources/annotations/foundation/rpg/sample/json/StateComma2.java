package foundation.rpg.sample.json;

import foundation.rpg.parser.UnexpectedInputException;

// Generated visitor pattern based state for grammar parser.
public class StateComma2 extends StackState<foundation.rpg.common.symbols.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>> {

// NoStack:
// Stack:
    public StateComma2(foundation.rpg.sample.json.JsonFactory factory, foundation.rpg.common.symbols.Comma node, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString7(getFactory(), symbol, this);
    }


// Accept:
}
