package com.designliquido.delegua.parser

import com.designliquido.delegua.DeleguaLanguage
import com.designliquido.delegua.DeleguaLexer
import com.designliquido.delegua.DeleguaParser
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.antlr.intellij.adaptor.lexer.ANTLRLexerAdaptor
import org.antlr.intellij.adaptor.lexer.PSIElementTypeFactory
import org.antlr.intellij.adaptor.parser.ANTLRParserAdaptor
import org.antlr.intellij.adaptor.psi.ANTLRPsiNode
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.tree.ParseTree

class DeleguaParserDefinition : ParserDefinition {

    companion object {
        val FILE = IFileElementType(DeleguaLanguage)
        val COMMENTS: TokenSet
        val STRINGS: TokenSet

        @Suppress("DEPRECATION")
        init {
            PSIElementTypeFactory.defineLanguageIElementTypes(
                DeleguaLanguage,
                DeleguaLexer.tokenNames,
                DeleguaParser.ruleNames
            )
            COMMENTS = PSIElementTypeFactory.createTokenSet(
                DeleguaLanguage,
                DeleguaLexer.MultiLinhaComentario,
                DeleguaLexer.SingleLinhaComentario,
                DeleguaLexer.HtmlComentario,
                DeleguaLexer.CDataComentario
            )
            STRINGS = PSIElementTypeFactory.createTokenSet(
                DeleguaLanguage,
                DeleguaLexer.LiteralTexto
            )
        }
    }

    override fun createLexer(project: Project?): Lexer =
        ANTLRLexerAdaptor(DeleguaLanguage, DeleguaLexer(null))

    override fun createParser(project: Project?): PsiParser {
        val parser = DeleguaParser(null)
        return object : ANTLRParserAdaptor(DeleguaLanguage, parser) {
            override fun parse(parser: Parser, root: IElementType): ParseTree =
                (parser as DeleguaParser).programa()
        }
    }

    override fun getWhitespaceTokens(): TokenSet = TokenSet.EMPTY
    override fun getCommentTokens(): TokenSet = COMMENTS
    override fun getStringLiteralElements(): TokenSet = STRINGS
    override fun getFileNodeType(): IFileElementType = FILE

    override fun createFile(viewProvider: FileViewProvider): PsiFile =
        DeleguaPsiFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement = ANTLRPsiNode(node)
}
