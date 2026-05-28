package com.designliquido.delegua.highlighting

import com.designliquido.delegua.DeleguaLanguage
import com.designliquido.delegua.DeleguaLexer
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.TokenIElementType

class DeleguaSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer =
        ANTLRLexerAdaptor(DeleguaLanguage, DeleguaLexer(null))

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        if (tokenType !is TokenIElementType) return EMPTY
        return when (tokenType.antlrTokenType) {
            DeleguaLexer.MultiLinhaComentario,
            DeleguaLexer.SingleLinhaComentario,
            DeleguaLexer.HtmlComentario,
            DeleguaLexer.CDataComentario -> pack(COMMENT)

            DeleguaLexer.LiteralTexto,
            DeleguaLexer.BackTick,
            DeleguaLexer.TemplateStringAtom -> pack(STRING)

            DeleguaLexer.DecimalLiteral,
            DeleguaLexer.HexInteiroLiteral,
            DeleguaLexer.OctalInteiroLiteral,
            DeleguaLexer.OctalInteiroLiteral2,
            DeleguaLexer.BinaryInteiroLiteral,
            DeleguaLexer.BigDecimalInteiroLiteral,
            DeleguaLexer.BigHexInteiroLiteral,
            DeleguaLexer.BigOctalInteiroLiteral,
            DeleguaLexer.BigBinaryInteiroLiteral -> pack(NUMBER)

            DeleguaLexer.LiteralNulo,
            DeleguaLexer.LiteralLogico,
            DeleguaLexer.Sustar, DeleguaLexer.Do, DeleguaLexer.Caso,
            DeleguaLexer.Senao, DeleguaLexer.Novo, DeleguaLexer.Var,
            DeleguaLexer.Pegue, DeleguaLexer.Cada, DeleguaLexer.Contem,
            DeleguaLexer.Finalmente, DeleguaLexer.Retorna, DeleguaLexer.Vazio,
            DeleguaLexer.Continue, DeleguaLexer.Para, DeleguaLexer.Escolha,
            DeleguaLexer.Enquanto, DeleguaLexer.Funcao_, DeleguaLexer.Isto,
            DeleguaLexer.Com, DeleguaLexer.Padrao, DeleguaLexer.Se,
            DeleguaLexer.Falhar, DeleguaLexer.Excluir, DeleguaLexer.Em,
            DeleguaLexer.Tente, DeleguaLexer.Como, DeleguaLexer.De,
            DeleguaLexer.Tendo, DeleguaLexer.Classe, DeleguaLexer.Enum,
            DeleguaLexer.Herda, DeleguaLexer.Super, DeleguaLexer.Const,
            DeleguaLexer.Exportar, DeleguaLexer.Importar, DeleguaLexer.Leia,
            DeleguaLexer.Escreva, DeleguaLexer.Extensao, DeleguaLexer.Abstrato,
            DeleguaLexer.Ajuda, DeleguaLexer.Assercao, DeleguaLexer.Construtor,
            DeleguaLexer.Estrangeira, DeleguaLexer.Mescla, DeleguaLexer.Operador,
            DeleguaLexer.Tipo, DeleguaLexer.Tudo, DeleguaLexer.Acumular,
            DeleguaLexer.Aguardar, DeleguaLexer.Assincrono, DeleguaLexer.Implementa,
            DeleguaLexer.Privado, DeleguaLexer.Publico, DeleguaLexer.Interface,
            DeleguaLexer.Pacote, DeleguaLexer.Protegido, DeleguaLexer.Estatico -> pack(KEYWORD)

            DeleguaLexer.Identificador -> pack(IDENTIFIER)

            else -> EMPTY
        }
    }

    companion object {
        val COMMENT = TextAttributesKey.createTextAttributesKey(
            "DELEGUA_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT
        )
        val STRING = TextAttributesKey.createTextAttributesKey(
            "DELEGUA_STRING", DefaultLanguageHighlighterColors.STRING
        )
        val NUMBER = TextAttributesKey.createTextAttributesKey(
            "DELEGUA_NUMBER", DefaultLanguageHighlighterColors.NUMBER
        )
        val KEYWORD = TextAttributesKey.createTextAttributesKey(
            "DELEGUA_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD
        )
        val IDENTIFIER = TextAttributesKey.createTextAttributesKey(
            "DELEGUA_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER
        )
        private val EMPTY = emptyArray<TextAttributesKey>()
    }
}
