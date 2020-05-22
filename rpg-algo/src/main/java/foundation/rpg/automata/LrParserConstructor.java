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

package foundation.rpg.automata;

import foundation.rpg.grammar.First;
import foundation.rpg.grammar.Grammar;
import foundation.rpg.grammar.Rule;
import foundation.rpg.grammar.Symbol;
import foundation.rpg.util.MapOfSets;

import java.util.*;
import java.util.stream.Collectors;

import static foundation.rpg.automata.LrItem.lrItem;
import static foundation.rpg.grammar.Symbol.any;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

public class LrParserConstructor {

    private final Grammar grammar;

    private final First first;
    private final Map<Symbol, Integer> counters = new LinkedHashMap<>();

    public LrParserConstructor(Grammar grammar) {
        this.grammar = grammar.augmented();
        this.first = new First(this.grammar);
    }

    public LrParserAutomata constructAutomata() {
        Queue<LrItemSet> processingSets = new LinkedList<>();
        LrItemSet start = closure(any, grammar.rulesFor(grammar.getStart()).stream().map(rule -> lrItem(rule, emptySet())).collect(toSet()));
        LrParserAutomata parser = new LrParserAutomata(start, grammar);
        parser.addState(start);
        processingSets.add(start);
        while(!processingSets.isEmpty()) {
            LrItemSet set = processingSets.poll();
            MapOfSets<Symbol, LrItem> transitions = transitions(parser, set);
            transitions.forEach((transitionSymbol, baseSet) -> {
                LrItemSet nextSet = closure(transitionSymbol, baseSet);
                if(parser.addState(nextSet)) {
                    processingSets.add(nextSet);
                }
                parser.transition(set, transitionSymbol, nextSet);
            });
        }
        return parser;
    }

    public MapOfSets<Symbol, LrItem> transitions(LrParserAutomata parser, LrItemSet set) {
        MapOfSets<Symbol, LrItem> transitions = new MapOfSets<>();
        set.getItems().forEach(lrItem -> {
            if(lrItem.isEnd()) {
                if(lrItem.getLookahead().isEmpty()) {
                    parser.accept(set, lrItem);
                } else {
                    lrItem.getLookahead().forEach(lookahead -> parser.reduction(set, lookahead, lrItem));
                }
            } else {
                transitions.add(lrItem.symbolAtDot(), lrItem.moveDot());
            }
        });
        return transitions;
    }

    public LrItemSet closure(Symbol symbol, Set<LrItem> base) {
        Queue<LrItem> queue = new ArrayDeque<>(base);
        Set<LrItem> closure = new LinkedHashSet<>();
        while(!queue.isEmpty()) {
            LrItem item = queue.poll();
            if(closure.add(item) && !item.isEnd()) {
                grammar.rulesFor(item.symbolAtDot()).stream().map(rule -> lrItem(rule, first.follow(item.afterDot(), item.getLookahead()))).forEach(queue::add);
            }
        }
        // Merge rules with same lookahead
        Map<Integer, Map<Rule, LrItem>> aggregator = new LinkedHashMap<>();
        closure.forEach(i -> aggregator.computeIfAbsent(i.getDot(), d -> new LinkedHashMap<>()).compute(i.getRule(), (r, t) -> {
            if(isNull(t)) return i;
            else return i.mergeLookahead(t);
        }));
        return new LrItemSet("" + symbol + counters.compute(symbol, (s, i) -> isNull(i) ? 1 : i+1), aggregator.entrySet().stream().flatMap(e -> e.getValue().values().stream()).collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public static LrParserAutomata generateParser(Grammar grammar) {
        return new LrParserConstructor(grammar).constructAutomata();
    }

}
