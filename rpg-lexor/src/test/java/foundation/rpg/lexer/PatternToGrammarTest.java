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

package foundation.rpg.lexer;

import foundation.rpg.Name;
import foundation.rpg.Pattern;
import foundation.rpg.automata.LrParserAutomata;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.parser.ParseErrorException;
import org.testng.annotations.Test;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.util.LinkedHashSet;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PatternToGrammarTest {

    @Test
    public void test() throws IOException, ParseErrorException {
        Element e1 = mock(Element.class);
        Element e2 = mock(Element.class);
        Element e3 = mock(Element.class);
        Element e4 = mock(Element.class);
        Element e5 = mock(Element.class);
        when(e1.getAnnotation(Name.class)).thenReturn(LexerGeneratorTest.E1.class.getAnnotation(Name.class));
        when(e2.getAnnotation(Name.class)).thenReturn(LexerGeneratorTest.E2.class.getAnnotation(Name.class));
        when(e3.getAnnotation(Name.class)).thenReturn(LexerGeneratorTest.E3.class.getAnnotation(Name.class));
        when(e4.getAnnotation(Pattern.class)).thenReturn(LexerGeneratorTest.E4.class.getAnnotation(Pattern.class));
        when(e5.getAnnotation(Pattern.class)).thenReturn(LexerGeneratorTest.E5.class.getAnnotation(Pattern.class));

        Grammar grammar = new PatternToGrammar().grammarFromPatterns(new LinkedHashSet<>(asList(e1, e2, e3, e4, e5)));

        System.out.println(grammar);

        LrParserAutomata lrParserAutomata = LexerConstructor.generateParser(grammar);
        System.out.println(lrParserAutomata);
    }

}