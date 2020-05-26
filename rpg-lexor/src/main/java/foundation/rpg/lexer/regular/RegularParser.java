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

package foundation.rpg.lexer.regular;

import foundation.rpg.gnfa.GNFA;
import foundation.rpg.gnfa.Thompson;
import foundation.rpg.parser.Input;
import foundation.rpg.parser.ParseErrorException;
import foundation.rpg.parser.Parser;

import java.io.IOException;
import java.io.StringReader;

import static foundation.rpg.parser.TokenInput.tokenInput;

public class RegularParser extends Parser<GNFA, State> {

    private static final Thompson thompson = new Thompson();
    private static final RegularLexer lexer = new RegularLexer();
    private static final RegularGNFAFactory factory = new RegularGNFAFactory(thompson);

    public RegularParser() {
        super(new State1(factory));
    }

    public GNFA parsePattern(String pattern) {
        try {
            return parse(tokenInput(new Input(pattern, new StringReader(pattern)), lexer));
        } catch (IOException | ParseErrorException e) {
            throw new IllegalArgumentException("Unable to parse /" + pattern + "/ " + e.getMessage(), e);
        }
    }

    public GNFA parseText(String text) {
        return thompson.string(text);
    }

}
