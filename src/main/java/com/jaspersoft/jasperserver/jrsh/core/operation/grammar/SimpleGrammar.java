//package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;
//
//import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.rule.Rule;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//public class SimpleGrammar implements Grammar {
//
//    private List<Rule> rules = new ArrayList<Rule>();
//
//    public SimpleGrammar(Rule... rules) {
//        Collections.addAll(this.rules, rules);
//    }
//
//    @Override
//    public List<Rule> getRules() {
//        return rules;
//    }
//
//    @Override
//    public void addRule(Rule rule) {
//        rules.add(rule);
//    }
//
//    @Override
//    public void addRules(Collection<Rule> rules) {
//        this.rules.addAll(rules);
//    }
//
//}
