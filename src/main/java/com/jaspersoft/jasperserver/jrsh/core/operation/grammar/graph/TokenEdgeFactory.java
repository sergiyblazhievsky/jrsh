package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.graph;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import org.jgrapht.EdgeFactory;

/**
 * @author Alexander Krasnyanskiy
 * @since 2.0
 */
public class TokenEdgeFactory implements EdgeFactory<Token, TokenEdge<Token>> {

    @Override
    public TokenEdge<Token> createEdge(Token source, Token target) {
        return new TokenEdge<Token>(source, target);
    }

}