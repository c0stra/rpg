/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Ondrej Fischer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package foundation.rpg.sample.language.ast;

/*

N = [Start, Program, ListOfStatement, Statement, Expression, ListOfExpression]
T = [End, Dot, Plus, Identifier, LPar, RPar, Comma]
S = Start
R = {
	Start -> Program,End
	Program -> ListOfStatement
	ListOfStatement -> 
	ListOfStatement -> ListOfStatement,Statement
	Statement -> Expression,Dot
	Expression -> Expression,Plus,Expression
	Expression -> Identifier
	Expression -> LPar,Expression,RPar
	Expression -> Identifier,LPar,ListOfExpression,RPar
	ListOfExpression -> 
	ListOfExpression -> ListOfExpression
	ListOfExpression -> Expression
	ListOfExpression -> ListOfExpression,Comma,Expression
}

1: {
	Start -> • Program End []
	Program -> • ListOfStatement [End]
	ListOfStatement -> • [End, Identifier, LPar]
	ListOfStatement -> • ListOfStatement Statement [End, Identifier, LPar]
}
Program1: {
	Start -> Program • End []
}
ListOfStatement1: {
	Program -> ListOfStatement • [End]
	ListOfStatement -> ListOfStatement • Statement [End, Identifier, LPar]
	Statement -> • Expression Dot [End, Identifier, LPar]
	Expression -> • Expression Plus Expression [Dot, Plus]
	Expression -> • Identifier [Dot, Plus]
	Expression -> • LPar Expression RPar [Dot, Plus]
	Expression -> • Identifier LPar ListOfExpression RPar [Dot, Plus]
}
End1: {
	Start -> Program End • []
}
Statement1: {
	ListOfStatement -> ListOfStatement Statement • [End, Identifier, LPar]
}
Expression1: {
	Statement -> Expression • Dot [End, Identifier, LPar]
	Expression -> Expression • Plus Expression [Dot, Plus]
}
Identifier1: {
	Expression -> Identifier • [Dot, Plus]
	Expression -> Identifier • LPar ListOfExpression RPar [Dot, Plus]
}
LPar1: {
	Expression -> LPar • Expression RPar [Dot, Plus]
	Expression -> • Expression Plus Expression [RPar, Plus]
	Expression -> • Identifier [RPar, Plus]
	Expression -> • LPar Expression RPar [RPar, Plus]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Plus]
}
Dot1: {
	Statement -> Expression Dot • [End, Identifier, LPar]
}
Plus1: {
	Expression -> Expression Plus • Expression [Dot, Plus]
	Expression -> • Expression Plus Expression [Dot, Plus]
	Expression -> • Identifier [Dot, Plus]
	Expression -> • LPar Expression RPar [Dot, Plus]
	Expression -> • Identifier LPar ListOfExpression RPar [Dot, Plus]
}
LPar2: {
	Expression -> Identifier LPar • ListOfExpression RPar [Dot, Plus]
	ListOfExpression -> • [RPar, Comma]
	ListOfExpression -> • ListOfExpression [RPar, Comma]
	ListOfExpression -> • Expression [RPar, Comma]
	ListOfExpression -> • ListOfExpression Comma Expression [RPar, Comma]
	Expression -> • Expression Plus Expression [RPar, Plus, Comma]
	Expression -> • Identifier [RPar, Plus, Comma]
	Expression -> • LPar Expression RPar [RPar, Plus, Comma]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Plus, Comma]
}
Expression2: {
	Expression -> LPar Expression • RPar [Dot, Plus]
	Expression -> Expression • Plus Expression [RPar, Plus]
}
Identifier2: {
	Expression -> Identifier • [RPar, Plus]
	Expression -> Identifier • LPar ListOfExpression RPar [RPar, Plus]
}
LPar3: {
	Expression -> LPar • Expression RPar [RPar, Plus]
	Expression -> • Expression Plus Expression [RPar, Plus]
	Expression -> • Identifier [RPar, Plus]
	Expression -> • LPar Expression RPar [RPar, Plus]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Plus]
}
Expression3: {
	Expression -> Expression Plus Expression • [Dot, Plus]
	Expression -> Expression • Plus Expression [Dot, Plus]
}
ListOfExpression1: {
	Expression -> Identifier LPar ListOfExpression • RPar [Dot, Plus]
	ListOfExpression -> ListOfExpression • [RPar, Comma]
	ListOfExpression -> ListOfExpression • Comma Expression [RPar, Comma]
}
Expression4: {
	ListOfExpression -> Expression • [RPar, Comma]
	Expression -> Expression • Plus Expression [RPar, Plus, Comma]
}
Identifier4: {
	Expression -> Identifier • [RPar, Plus, Comma]
	Expression -> Identifier • LPar ListOfExpression RPar [RPar, Plus, Comma]
}
LPar5: {
	Expression -> LPar • Expression RPar [RPar, Plus, Comma]
	Expression -> • Expression Plus Expression [RPar, Plus]
	Expression -> • Identifier [RPar, Plus]
	Expression -> • LPar Expression RPar [RPar, Plus]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Plus]
}
RPar1: {
	Expression -> LPar Expression RPar • [Dot, Plus]
}
Plus2: {
	Expression -> Expression Plus • Expression [RPar, Plus]
	Expression -> • Expression Plus Expression [RPar, Plus]
	Expression -> • Identifier [RPar, Plus]
	Expression -> • LPar Expression RPar [RPar, Plus]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Plus]
}
LPar6: {
	Expression -> Identifier LPar • ListOfExpression RPar [RPar, Plus]
	ListOfExpression -> • [RPar, Comma]
	ListOfExpression -> • ListOfExpression [RPar, Comma]
	ListOfExpression -> • Expression [RPar, Comma]
	ListOfExpression -> • ListOfExpression Comma Expression [RPar, Comma]
	Expression -> • Expression Plus Expression [RPar, Plus, Comma]
	Expression -> • Identifier [RPar, Plus, Comma]
	Expression -> • LPar Expression RPar [RPar, Plus, Comma]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Plus, Comma]
}
Expression5: {
	Expression -> LPar Expression • RPar [RPar, Plus]
	Expression -> Expression • Plus Expression [RPar, Plus]
}
RPar2: {
	Expression -> Identifier LPar ListOfExpression RPar • [Dot, Plus]
}
Comma1: {
	ListOfExpression -> ListOfExpression Comma • Expression [RPar, Comma]
	Expression -> • Expression Plus Expression [RPar, Comma, Plus]
	Expression -> • Identifier [RPar, Comma, Plus]
	Expression -> • LPar Expression RPar [RPar, Comma, Plus]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Comma, Plus]
}
Plus4: {
	Expression -> Expression Plus • Expression [RPar, Plus, Comma]
	Expression -> • Expression Plus Expression [RPar, Plus, Comma]
	Expression -> • Identifier [RPar, Plus, Comma]
	Expression -> • LPar Expression RPar [RPar, Plus, Comma]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Plus, Comma]
}
LPar8: {
	Expression -> Identifier LPar • ListOfExpression RPar [RPar, Plus, Comma]
	ListOfExpression -> • [RPar, Comma]
	ListOfExpression -> • ListOfExpression [RPar, Comma]
	ListOfExpression -> • Expression [RPar, Comma]
	ListOfExpression -> • ListOfExpression Comma Expression [RPar, Comma]
	Expression -> • Expression Plus Expression [RPar, Plus, Comma]
	Expression -> • Identifier [RPar, Plus, Comma]
	Expression -> • LPar Expression RPar [RPar, Plus, Comma]
	Expression -> • Identifier LPar ListOfExpression RPar [RPar, Plus, Comma]
}
Expression6: {
	Expression -> LPar Expression • RPar [RPar, Plus, Comma]
	Expression -> Expression • Plus Expression [RPar, Plus]
}
Expression7: {
	Expression -> Expression Plus Expression • [RPar, Plus]
	Expression -> Expression • Plus Expression [RPar, Plus]
}
ListOfExpression2: {
	Expression -> Identifier LPar ListOfExpression • RPar [RPar, Plus]
	ListOfExpression -> ListOfExpression • [RPar, Comma]
	ListOfExpression -> ListOfExpression • Comma Expression [RPar, Comma]
}
RPar3: {
	Expression -> LPar Expression RPar • [RPar, Plus]
}
Expression9: {
	ListOfExpression -> ListOfExpression Comma Expression • [RPar, Comma]
	Expression -> Expression • Plus Expression [RPar, Comma, Plus]
}
Expression10: {
	Expression -> Expression Plus Expression • [RPar, Plus, Comma]
	Expression -> Expression • Plus Expression [RPar, Plus, Comma]
}
ListOfExpression3: {
	Expression -> Identifier LPar ListOfExpression • RPar [RPar, Plus, Comma]
	ListOfExpression -> ListOfExpression • [RPar, Comma]
	ListOfExpression -> ListOfExpression • Comma Expression [RPar, Comma]
}
RPar4: {
	Expression -> LPar Expression RPar • [RPar, Plus, Comma]
}
RPar5: {
	Expression -> Identifier LPar ListOfExpression RPar • [RPar, Plus]
}
RPar6: {
	Expression -> Identifier LPar ListOfExpression RPar • [RPar, Plus, Comma]
}

1: End -> REDUCE: ListOfStatement -> • [End, Identifier, LPar]
1: Identifier -> REDUCE: ListOfStatement -> • [End, Identifier, LPar]
1: LPar -> REDUCE: ListOfStatement -> • [End, Identifier, LPar]
1: Program -> GOTO: Program1
1: ListOfStatement -> GOTO: ListOfStatement1
Program1: End -> GOTO: End1
ListOfStatement1: End -> REDUCE: Program -> ListOfStatement • [End]
ListOfStatement1: Statement -> GOTO: Statement1
ListOfStatement1: Expression -> GOTO: Expression1
ListOfStatement1: Identifier -> GOTO: Identifier1
ListOfStatement1: LPar -> GOTO: LPar1
End1:  -> ACCEPT: Start -> Program End • []
Statement1: End -> REDUCE: ListOfStatement -> ListOfStatement Statement • [End, Identifier, LPar]
Statement1: Identifier -> REDUCE: ListOfStatement -> ListOfStatement Statement • [End, Identifier, LPar]
Statement1: LPar -> REDUCE: ListOfStatement -> ListOfStatement Statement • [End, Identifier, LPar]
Expression1: Dot -> GOTO: Dot1
Expression1: Plus -> GOTO: Plus1
Identifier1: Dot -> REDUCE: Expression -> Identifier • [Dot, Plus]
Identifier1: Plus -> REDUCE: Expression -> Identifier • [Dot, Plus]
Identifier1: LPar -> GOTO: LPar2
LPar1: Expression -> GOTO: Expression2
LPar1: Identifier -> GOTO: Identifier2
LPar1: LPar -> GOTO: LPar3
Dot1: End -> REDUCE: Statement -> Expression Dot • [End, Identifier, LPar]
Dot1: Identifier -> REDUCE: Statement -> Expression Dot • [End, Identifier, LPar]
Dot1: LPar -> REDUCE: Statement -> Expression Dot • [End, Identifier, LPar]
Plus1: Expression -> GOTO: Expression3
Plus1: Identifier -> GOTO: Identifier1
Plus1: LPar -> GOTO: LPar1
LPar2: RPar -> REDUCE: ListOfExpression -> • [RPar, Comma]
LPar2: Comma -> REDUCE: ListOfExpression -> • [RPar, Comma]
LPar2: ListOfExpression -> GOTO: ListOfExpression1
LPar2: Expression -> GOTO: Expression4
LPar2: Identifier -> GOTO: Identifier4
LPar2: LPar -> GOTO: LPar5
Expression2: RPar -> GOTO: RPar1
Expression2: Plus -> GOTO: Plus2
Identifier2: RPar -> REDUCE: Expression -> Identifier • [RPar, Plus]
Identifier2: Plus -> REDUCE: Expression -> Identifier • [RPar, Plus]
Identifier2: LPar -> GOTO: LPar6
LPar3: Expression -> GOTO: Expression5
LPar3: Identifier -> GOTO: Identifier2
LPar3: LPar -> GOTO: LPar3
Expression3: Dot -> REDUCE: Expression -> Expression Plus Expression • [Dot, Plus]
Expression3: Plus -> REDUCE: Expression -> Expression Plus Expression • [Dot, Plus]
ListOfExpression1: RPar -> REDUCE: ListOfExpression -> ListOfExpression • [RPar, Comma]
ListOfExpression1: Comma -> REDUCE: ListOfExpression -> ListOfExpression • [RPar, Comma]
Expression4: RPar -> REDUCE: ListOfExpression -> Expression • [RPar, Comma]
Expression4: Comma -> REDUCE: ListOfExpression -> Expression • [RPar, Comma]
Expression4: Plus -> GOTO: Plus4
Identifier4: RPar -> REDUCE: Expression -> Identifier • [RPar, Plus, Comma]
Identifier4: Plus -> REDUCE: Expression -> Identifier • [RPar, Plus, Comma]
Identifier4: Comma -> REDUCE: Expression -> Identifier • [RPar, Plus, Comma]
Identifier4: LPar -> GOTO: LPar8
LPar5: Expression -> GOTO: Expression6
LPar5: Identifier -> GOTO: Identifier2
LPar5: LPar -> GOTO: LPar3
RPar1: Dot -> REDUCE: Expression -> LPar Expression RPar • [Dot, Plus]
RPar1: Plus -> REDUCE: Expression -> LPar Expression RPar • [Dot, Plus]
Plus2: Expression -> GOTO: Expression7
Plus2: Identifier -> GOTO: Identifier2
Plus2: LPar -> GOTO: LPar3
LPar6: RPar -> REDUCE: ListOfExpression -> • [RPar, Comma]
LPar6: Comma -> REDUCE: ListOfExpression -> • [RPar, Comma]
LPar6: ListOfExpression -> GOTO: ListOfExpression2
LPar6: Expression -> GOTO: Expression4
LPar6: Identifier -> GOTO: Identifier4
LPar6: LPar -> GOTO: LPar5
Expression5: RPar -> GOTO: RPar3
Expression5: Plus -> GOTO: Plus2
RPar2: Dot -> REDUCE: Expression -> Identifier LPar ListOfExpression RPar • [Dot, Plus]
RPar2: Plus -> REDUCE: Expression -> Identifier LPar ListOfExpression RPar • [Dot, Plus]
Comma1: Expression -> GOTO: Expression9
Comma1: Identifier -> GOTO: Identifier4
Comma1: LPar -> GOTO: LPar5
Plus4: Expression -> GOTO: Expression10
Plus4: Identifier -> GOTO: Identifier4
Plus4: LPar -> GOTO: LPar5
LPar8: RPar -> REDUCE: ListOfExpression -> • [RPar, Comma]
LPar8: Comma -> REDUCE: ListOfExpression -> • [RPar, Comma]
LPar8: ListOfExpression -> GOTO: ListOfExpression3
LPar8: Expression -> GOTO: Expression4
LPar8: Identifier -> GOTO: Identifier4
LPar8: LPar -> GOTO: LPar5
Expression6: RPar -> GOTO: RPar4
Expression6: Plus -> GOTO: Plus2
Expression7: RPar -> REDUCE: Expression -> Expression Plus Expression • [RPar, Plus]
Expression7: Plus -> REDUCE: Expression -> Expression Plus Expression • [RPar, Plus]
ListOfExpression2: RPar -> REDUCE: ListOfExpression -> ListOfExpression • [RPar, Comma]
ListOfExpression2: Comma -> REDUCE: ListOfExpression -> ListOfExpression • [RPar, Comma]
RPar3: RPar -> REDUCE: Expression -> LPar Expression RPar • [RPar, Plus]
RPar3: Plus -> REDUCE: Expression -> LPar Expression RPar • [RPar, Plus]
Expression9: RPar -> REDUCE: ListOfExpression -> ListOfExpression Comma Expression • [RPar, Comma]
Expression9: Comma -> REDUCE: ListOfExpression -> ListOfExpression Comma Expression • [RPar, Comma]
Expression9: Plus -> GOTO: Plus4
Expression10: RPar -> REDUCE: Expression -> Expression Plus Expression • [RPar, Plus, Comma]
Expression10: Plus -> REDUCE: Expression -> Expression Plus Expression • [RPar, Plus, Comma]
Expression10: Comma -> REDUCE: Expression -> Expression Plus Expression • [RPar, Plus, Comma]
ListOfExpression3: RPar -> REDUCE: ListOfExpression -> ListOfExpression • [RPar, Comma]
ListOfExpression3: Comma -> REDUCE: ListOfExpression -> ListOfExpression • [RPar, Comma]
RPar4: RPar -> REDUCE: Expression -> LPar Expression RPar • [RPar, Plus, Comma]
RPar4: Plus -> REDUCE: Expression -> LPar Expression RPar • [RPar, Plus, Comma]
RPar4: Comma -> REDUCE: Expression -> LPar Expression RPar • [RPar, Plus, Comma]
RPar5: RPar -> REDUCE: Expression -> Identifier LPar ListOfExpression RPar • [RPar, Plus]
RPar5: Plus -> REDUCE: Expression -> Identifier LPar ListOfExpression RPar • [RPar, Plus]
RPar6: RPar -> REDUCE: Expression -> Identifier LPar ListOfExpression RPar • [RPar, Plus, Comma]
RPar6: Plus -> REDUCE: Expression -> Identifier LPar ListOfExpression RPar • [RPar, Plus, Comma]
RPar6: Comma -> REDUCE: Expression -> Identifier LPar ListOfExpression RPar • [RPar, Plus, Comma]

*/

import foundation.rpg.parser.UnexpectedInputException;
import foundation.rpg.parser.StateBase;

// Generated visitor pattern based state for grammar parser.
public class State extends StateBase<foundation.rpg.sample.language.ast.Program> {

// Ignored:
    public State visitWhiteSpace(foundation.rpg.common.WhiteSpace symbol) {
        return this;
    }

    public State visitComment(foundation.rpg.common.Comment symbol) {
        return this;
    }


// Symbols:
    public State visitEnd(foundation.rpg.parser.End symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitDot(foundation.rpg.common.Dot symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitPlus(foundation.rpg.common.Plus symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitIdentifier(foundation.rpg.sample.language.ast.Identifier symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitLPar(foundation.rpg.common.LPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitRPar(foundation.rpg.common.RPar symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitComma(foundation.rpg.common.Comma symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitProgram(foundation.rpg.sample.language.ast.Program symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitListOfStatement(java.util.List<foundation.rpg.sample.language.ast.Statement> symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitStatement(foundation.rpg.sample.language.ast.Statement symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitExpression(foundation.rpg.sample.language.ast.Expression symbol) throws UnexpectedInputException {
        return error(symbol);
    }

    public State visitListOfExpression(java.util.List<foundation.rpg.sample.language.ast.Expression> symbol) throws UnexpectedInputException {
        return error(symbol);
    }


}
