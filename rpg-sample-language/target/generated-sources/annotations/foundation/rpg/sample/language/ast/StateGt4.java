package foundation.rpg.sample.language.ast;

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.Named;

import java.util.Arrays;
import java.util.List;

// Generated visitor pattern based state for grammar parser.
public class StateGt4 extends StackState<foundation.rpg.common.symbols.Gt, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State>> {

// NoStack:
// Stack:
    public StateGt4(foundation.rpg.sample.language.ast.AstFactory factory, foundation.rpg.common.symbols.Gt node, StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> prev) {
        super(factory, node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitAdditiveExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAdditiveExpression17(getFactory(), symbol, this);
    }

    @Override
    public State visitMultiplicativeExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateMultiplicativeExpression6(getFactory(), symbol, this);
    }

    @Override
    public State visitAtomicExpression(foundation.rpg.sample.language.ast.Expression symbol) {
        return new StateAtomicExpression6(getFactory(), symbol, this);
    }

    @Override
    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) {
        return new StateIdentifier6(getFactory(), symbol, this);
    }

    @Override
    public State visitLong(java.lang.Long symbol) {
        return new StateLong6(getFactory(), symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString6(getFactory(), symbol, this);
    }

    @Override
    public State visitLPar(foundation.rpg.common.symbols.LPar symbol) {
        return new StateLPar8(getFactory(), symbol, this);
    }


// Accept:
    @Override
    public List<Object> stack() {
        StackState<foundation.rpg.sample.language.ast.Expression, ? extends State> stack1 = this.getPrev();
		State stack2 = stack1.getPrev();
        return Arrays.asList(stack1.getNode(), this.getNode());
    }

}
