package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.rule.Rule;

import java.util.Collection;
import java.util.List;

public interface Grammar {

    List<Rule> getRules();

    void addRule(Rule rule);

    void addRules(Collection<Rule> rules);

}
