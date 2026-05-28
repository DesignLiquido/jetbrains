package com.designliquido.delegua;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class DeleguaLexerBase extends Lexer {

    private final Deque<Integer> templateDepthStack = new ArrayDeque<>();

    protected DeleguaLexerBase(CharStream input) {
        super(input);
    }

    protected boolean IsStartOfFile() {
        return _tokenStartCharIndex == 0;
    }

    protected boolean IsStrictMode() {
        return false;
    }

    protected boolean IsRegexPossible() {
        return false;
    }

    protected boolean EstaEmTemplateTexto() {
        return !templateDepthStack.isEmpty();
    }

    protected void ProcessoAbreChave() {
        if (!templateDepthStack.isEmpty()) {
            templateDepthStack.push(templateDepthStack.pop() + 1);
        }
    }

    protected void ProcessoFechaChave() {
        if (!templateDepthStack.isEmpty() && templateDepthStack.peek() > 0) {
            templateDepthStack.push(templateDepthStack.pop() - 1);
        }
    }

    protected void IncrementarProfundidadeTemplate() {
        templateDepthStack.push(1);
    }

    protected void DecreaseTemplateDepth() {
        if (!templateDepthStack.isEmpty()) {
            templateDepthStack.pop();
        }
    }

    protected void ProcessoLiteralTexto() {
        // noop
    }
}
