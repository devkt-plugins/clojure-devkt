package org.intellij.clojure.devkt

import org.ice1000.devkt.openapi.*
import org.intellij.clojure.devkt.lang.*
import org.intellij.clojure.devkt.psi.*
import org.intellij.clojure.devkt.psi.ClojureTypes.*
import org.intellij.clojure.devkt.util.*
import org.jetbrains.kotlin.com.intellij.lexer.Lexer
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

class Clojure<TextAttributes> : ExtendedDevKtLanguage<TextAttributes>(
		ClojureLanguage,
		ClojureParserDefinition
) {
	override val lineCommentStart: String get() = ";"
	override fun satisfies(fileName: String) = fileName == "build.boot" ||
			fileName.endsWith(".clj") ||
			fileName.endsWith(".cljs") ||
			fileName.endsWith(".cljc")

	override fun createLexer(project: Project): Lexer {
		return ClojureHighlightingLexer(ClojureLanguage)
	}

	/**
	 * @param type IElementType
	 * @param colorScheme ColorScheme<TextAttributes>
	 * @return TextAttributes?
	 */
	override fun attributesOf(type: IElementType, colorScheme: ColorScheme<TextAttributes>) = when (type) {
		ClojureTokens.LINE_COMMENT -> colorScheme.lineComments
		C_STRING -> colorScheme.string
		C_CHAR -> colorScheme.charLiteral
		C_NUMBER, C_HEXNUM, C_RDXNUM, C_RATIO -> colorScheme.numbers
		C_BOOL -> colorScheme.keywords
		C_NIL -> colorScheme.keywords
		C_DOT, C_DOTDASH -> colorScheme.colon
		C_COLONCOLON -> colorScheme.colon
		C_COLON -> colorScheme.colon
		C_SYM -> colorScheme.identifiers
		C_COMMA -> colorScheme.comma
		C_SLASH -> colorScheme.operators
		C_SYNTAX_QUOTE -> colorScheme.operators
		C_QUOTE -> colorScheme.string
		C_TILDE, C_TILDE_AT -> colorScheme.operators
		C_AT -> colorScheme.operators
		C_HAT, C_SHARP_HAT -> colorScheme.metaData
		C_SHARP, C_SHARP_COMMENT, C_SHARP_EQ, C_SHARP_NS -> colorScheme.macro
		C_SHARP_QMARK, C_SHARP_QMARK_AT, C_SHARP_QUOTE -> colorScheme.macro
		C_PAREN1, C_PAREN2 -> colorScheme.parentheses
		C_BRACE1, C_BRACE2 -> colorScheme.braces
		C_BRACKET1, C_BRACKET2 -> colorScheme.brackets
		ClojureHighlightingLexer.CALLABLE -> colorScheme.keywords
		ClojureHighlightingLexer.KEYWORD -> colorScheme.keywords
		ClojureHighlightingLexer.CALLABLE_KEYWORD -> colorScheme.annotations
		ClojureHighlightingLexer.QUOTED_SYM -> colorScheme.macro
		else -> null
	}

	/**
	 * @param element PsiElement
	 * @param document AnnotationHolder<TextAttributes>
	 * @param colorScheme ColorScheme<TextAttributes>
	 */
	override fun annotate(
			element: PsiElement,
			document: AnnotationHolder<TextAttributes>,
			colorScheme: ColorScheme<TextAttributes>) {
		val callable = element is CSForm && element.parentForm.let {
			it is CList && it.firstForm == element && it.iterate(CReaderMacro::class).isEmpty
		}
		if (callable) document.highlight(element.valueRange, colorScheme.keywords)
		when (element) {
			is PsiErrorElement -> document.highlight(element, colorScheme.unknown)
			is CMetadata -> element.firstForm.let {
				if (it is CSymbol) document.highlight(it, colorScheme.metaData)
			}
		}
		if (element is CForm && element.iterate(CReaderMacro::class)
						.find { it.firstChild.elementType == ClojureTypes.C_SHARP_COMMENT } != null)
			document.highlight(element, colorScheme.docComments)
	}
}
