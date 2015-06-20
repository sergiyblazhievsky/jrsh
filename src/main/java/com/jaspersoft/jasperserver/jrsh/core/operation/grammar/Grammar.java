package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import java.util.Collection;
import java.util.List;

/**
 * Basically, grammar is a set of production rules
 * for strings in a formal language.
 *
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public interface Grammar {

    List<Rule> getRules();

    void addRule(Rule rule);

    void addRules(Collection<Rule> rules);

}
