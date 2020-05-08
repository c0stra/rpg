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

import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.lexer.pattern.*;

import java.util.*;

import static foundation.rpg.grammar.Grammar.grammar;
import static foundation.rpg.grammar.Rule.rule;
import static foundation.rpg.grammar.Symbol.symbol;
import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class PatternToGrammar {

    private final Map<Object, Symbol> map = new LinkedHashMap<>();
    private Symbol of(Object o) {
        return map.computeIfAbsent(o, k -> symbol(o.toString()));
    }

    public final Grammar grammarFromPatterns(List<Pattern> patterns) {
        Set<Rule> rules = new LinkedHashSet<>();
        Symbol start = symbol("Token");
        for(Pattern pattern : patterns) {
            Symbol patternSymbol = of(pattern);
            //rules.add(rule(start, singletonList(patternSymbol), 10));
            pattern.accept(new RulesVisitor(rules, start, emptyList()));
        }
        return grammar(start, rules, emptySet());
    }

    private class RulesVisitor implements Visitor {
        private final Set<Rule> rules;
        private final Symbol left;
        private final List<Symbol> append;

        public RulesVisitor(Set<Rule> rules, Symbol left, List<Symbol> append) {
            this.rules = rules;
            this.left = left;
            this.append = append;
        }

        List<Symbol> concat(Symbol... s) {
            List<Symbol> r = new ArrayList<>(append.size() + s.length);
            r.addAll(asList(s));
            r.addAll(append);
            return r;
        }

        RulesVisitor rules(Symbol left, List<Symbol> append) {
            return new RulesVisitor(rules, left, new ArrayList<>(append));
        }

        @Override
        public void visitOptions(Pattern pattern) {
            pattern.getOptions().forEach(option -> {
                rules.add(rule(left, singletonList(of(option))));
                rules(left, append).visitOption(option);
            });
        }

        @Override
        public void visitOption(Option option) {
            if(isNull(option)) return;
            Symbol symbol = of(option);
            if(nonNull(option.getSuffix())) {
                rules(symbol, append).visitOption(option.getSuffix());
                option.getPrefix().accept(rules(symbol, singletonList(of(option.getSuffix()))));
            } else {
                option.getPrefix().accept(rules(symbol, append));
            }
        }

        @Override
        public void visitAnyTimes(AnyTimes anyTimes) {
            rules.add(rule(left, append));
            //Symbol left = of(anyTimes);
            //rules.add(rule(left, asList(of(anyTimes.getChunk()), left)));
            anyTimes.getChunk().accept(rules(left, singletonList(left)));
        }

        @Override
        public void visitAtLeastOnce(AtLeastOnce node) {
            Symbol left = of(node);
            Symbol chunkSymbol = of(node.getChunk());
            rules.add(rule(left, concat(chunkSymbol)));
            rules.add(rule(left, concat(of(node.getChunk()), left)));
            node.getChunk().accept(rules(this.left, concat(left)));
        }

        @Override
        public void visitChar(Char node) {
            rules.add(rule(left, concat(of(node))));
        }

        @Override
        public void visitGroup(Group node) {
            rules.add(rule(left, concat(of(node))));
        }

        @Override
        public void visitChars(Chars chars) {
            //characters.forEach(this::visitChar);
        }

        @Override
        public void visitNot(Not not) {
            rules.add(rule(left, concat(of(not))));
        }

    }
}
