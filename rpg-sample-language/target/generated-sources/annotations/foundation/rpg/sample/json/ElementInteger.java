package foundation.rpg.sample.json;

import foundation.rpg.parser.Element;
import foundation.rpg.parser.UnexpectedInputException;

// Generated token element wrapper for grammar parser.
public class ElementInteger implements Element<State> {
    private final java.lang.Integer symbol;

    public ElementInteger(java.lang.Integer symbol) {
        this.symbol = symbol;
    }

    @Override
    public State accept(State state) throws UnexpectedInputException {
        return state.visitInteger(symbol);
    }

    @Override
    public String toString() {
        return symbol.toString();
    }
}
