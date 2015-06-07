package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.graph;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import org.jgrapht.EdgeFactory;

/**
 * @author Alexander Krasnyanskiy
 */
public class TokenEdgeFactory implements EdgeFactory<Token, TokenEdge<Token>> {

    @Override
    public TokenEdge<Token> createEdge(Token source, Token target) {
        return new TokenEdge<>(source, target);
    }

}