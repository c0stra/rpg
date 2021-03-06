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

package foundation.rpg.parser;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.joining;

public interface TokenInput<S> {

    Element<S> next() throws SyntaxError, IOException;

    Position position();

    void error(Position mark, String message);

    default void error(Position mark, Object state, List<?> expected, Object unexpectedSymbol) {
        error(mark, "Expected " + (expected.size() == 1 ? expected.get(0) : expected.stream().map(Object::toString).collect(joining(", ", " one of: ", ""))) + ", but got " + unexpectedSymbol);
    }

    static <S> TokenInput<S> tokenInput(Input input, Lexer<S> lexer) {
        return new TokenInput<S>() {
            @Override public Element<S> next() throws SyntaxError, IOException {
                return lexer.next(input);
            }
            @Override public Position position() {
                return input.position();
            }
            @Override public void error(Position mark, String message) {
                //input.error(message);
            }
        };
    }

}
