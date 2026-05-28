package com.designliquido.delegua;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

public abstract class DeleguaParserBase extends Parser {

    protected DeleguaParserBase(TokenStream input) {
        super(input);
    }

    private boolean hasLineTerminatorBefore() {
        int currentIndex = getCurrentToken().getTokenIndex();
        for (int i = currentIndex - 1; i >= 0; i--) {
            Token t = _input.get(i);
            if (t.getChannel() == Token.DEFAULT_CHANNEL) break;
            String text = t.getText();
            if (text.contains("\r") || text.contains("\n") ||
                    text.contains("\u2028") || text.contains("\u2029")) {
                return true;
            }
        }
        return false;
    }

    protected boolean notLinhaTerminador() {
        return !hasLineTerminatorBefore();
    }

    protected boolean lineTerminadorAhead() {
        return hasLineTerminatorBefore();
    }

    protected boolean closeChave() {
        return getCurrentToken().getType() == DeleguaLexer.FechaChave;
    }

    protected boolean notAbreChaveAndNotFunction() {
        int type = getCurrentToken().getType();
        return type != DeleguaLexer.AbreChave && type != DeleguaLexer.Funcao_;
    }

    protected boolean p(String value) {
        return getCurrentToken().getText().equals(value);
    }

    protected boolean n(String value) {
        return getCurrentToken().getText().equals(value);
    }
}
