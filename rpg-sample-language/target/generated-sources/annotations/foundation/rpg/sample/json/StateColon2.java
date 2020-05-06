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

package foundation.rpg.sample.json;

/*

Colon2: {
	MapOfObject -> MapOfObject Comma String Colon • Object [RCurl, Comma]
	Object -> • String [RCurl, Comma]
	Object -> • Integer [RCurl, Comma]
	Object -> • Double [RCurl, Comma]
	Object -> • LBr ListOfObject RBr [RCurl, Comma]
	Object -> • LBr RBr [RCurl, Comma]
	Object -> • LCurl MapOfObject RCurl [RCurl, Comma]
	Object -> • LCurl RCurl [RCurl, Comma]
}

*/

import foundation.rpg.parser.UnexpectedInputException;
import javax.annotation.Generated;

@Generated("Generated visitor pattern based state for grammar parser.")
public class StateColon2 extends StackState<foundation.rpg.common.Colon, StackState<java.lang.String, StackState<foundation.rpg.common.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>>> {
// Stack:
    public StateColon2(foundation.rpg.common.Colon node, StackState<java.lang.String, StackState<foundation.rpg.common.Comma, StackState<java.util.Map<java.lang.String,java.lang.Object>, ? extends State>>> prev) {
        super(node, prev);
    }


// Reduce:
// Shift:
    @Override
    public State visitObject(java.lang.Object symbol) {
        return new StateObject7(symbol, this);
    }

    @Override
    public State visitString(java.lang.String symbol) {
        return new StateString8(symbol, this);
    }

    @Override
    public State visitInteger(java.lang.Integer symbol) {
        return new StateInteger5(symbol, this);
    }

    @Override
    public State visitDouble(java.lang.Double symbol) {
        return new StateDouble5(symbol, this);
    }

    @Override
    public State visitLBr(foundation.rpg.common.LBr symbol) {
        return new StateLBr5(symbol, this);
    }

    @Override
    public State visitLCurl(foundation.rpg.common.LCurl symbol) {
        return new StateLCurl5(symbol, this);
    }


// Accept:
}