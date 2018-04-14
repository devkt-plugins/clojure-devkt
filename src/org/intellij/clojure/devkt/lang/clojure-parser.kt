/*
 * Copyright 2016-present Greg Shrago
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.intellij.clojure.devkt.lang

import org.intellij.clojure.devkt.parser.ClojureParser
import org.intellij.clojure.devkt.parser._ClojureLexer
import org.intellij.clojure.devkt.psi.ClojureTypes
import org.intellij.clojure.devkt.psi.ClojureTypes.*
import org.intellij.clojure.devkt.util.wsOrComment
import org.jetbrains.kotlin.com.intellij.lang.*
import org.jetbrains.kotlin.com.intellij.lang.parser.GeneratedParserUtilBase
import org.jetbrains.kotlin.com.intellij.lexer.*
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet

/**
 * @author gregsh
 */
class ClojureLexer(language: Language) : FlexAdapter(_ClojureLexer(language))

object ClojureParserDefinition : ClojureParserDefinitionBase() {
	override fun getFileNodeType() = ClojureTokens.CLJ_FILE_TYPE
}

class ClojureHighlightingLexer(language: Language) : LookAheadLexer(ClojureLexer(language)) {
	companion object {
		val CALLABLE = IElementType("C_CALLABLE*", ClojureLanguage)
		val KEYWORD = IElementType("C_KEYWORD*", ClojureLanguage)
		val CALLABLE_KEYWORD = IElementType("C_CALLABLE_KEYWORD*", ClojureLanguage)
		val QUOTED_SYM = IElementType("C_QUOTED_SYM*", ClojureLanguage)
	}

	override fun lookAhead(baseLexer: Lexer) {
		fun skipWs(l: Lexer) {
			while (l.tokenType.let {
						ClojureTokens.WHITESPACES.contains(it) ||
								ClojureTokens.COMMENTS.contains(it)
					}) advanceLexer(l)
		}

		val tokenType0 = baseLexer.tokenType

		when (tokenType0) {
			C_SHARP -> {
				baseLexer.advance()
				when (baseLexer.tokenType) {
					C_STRING,
					C_PAREN1,
					C_BRACE1 -> advanceAs(baseLexer, baseLexer.tokenType)
					else -> addToken(baseLexer.tokenStart, C_SHARP)
				}
			}
			C_QUOTE -> {
				advanceAs(baseLexer, tokenType0)
				skipWs(baseLexer)
				if (baseLexer.tokenType === C_SYM) advanceSymbolAs(baseLexer, QUOTED_SYM)
				else advanceLexer(baseLexer)
			}
			C_COLON,
			C_COLONCOLON -> {
				advanceAs(baseLexer, tokenType0)
				if (baseLexer.tokenType === C_SYM) {
					advanceAs(baseLexer, KEYWORD)
					if (baseLexer.tokenType === C_SLASH) advanceAs(baseLexer, KEYWORD)
					if (baseLexer.tokenType === C_SYM) advanceAs(baseLexer, KEYWORD)
				}
			}
			C_PAREN1 -> {
				advanceAs(baseLexer, tokenType0)
				skipWs(baseLexer)
				val callableType = when {
					baseLexer.tokenType.let { it == C_COLON || it == C_COLONCOLON } -> CALLABLE_KEYWORD
					else -> CALLABLE
				}
				advanceSymbolAs(baseLexer, callableType)
			}
			else -> super.lookAhead(baseLexer)
		}
	}

	private fun advanceSymbolAs(baseLexer: Lexer, type: IElementType) {
		w@ while (true) {
			val tokenType = baseLexer.tokenType
			when (tokenType) {
				C_DOT, C_DOTDASH -> advanceAs(baseLexer, tokenType)
				C_SLASH, C_SYM -> advanceAs(baseLexer, type)
				C_COLON, C_COLONCOLON -> advanceAs(baseLexer, type)
				else -> break@w
			}
		}
	}
}

abstract class ClojureParserDefinitionBase : ParserDefinition {

	override fun createLexer(project: Project?) = ClojureLexer(fileNodeType.language)
	override fun createParser(project: Project?) = ClojureParser()
	override fun createFile(viewProvider: FileViewProvider) = CFileImpl(viewProvider, fileNodeType.language)
	override fun createElement(node: ASTNode) = ClojureTypes.Factory.createElement(node.elementType)

	override fun getStringLiteralElements() = ClojureTokens.STRINGS
	override fun getWhitespaceTokens() = ClojureTokens.WHITESPACES
	override fun getCommentTokens() = ClojureTokens.COMMENTS

	override fun spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements {
		val lt = left.elementType
		val rt = right.elementType
		if (rt == C_COMMA || lt in ClojureTokens.MACROS || lt in ClojureTokens.SHARPS)
			return ParserDefinition.SpaceRequirements.MUST_NOT
		if (lt == C_DOT || lt == C_DOTDASH ||
				lt == C_SLASH && rt == C_SYM ||
				lt == C_SYM && rt == C_SLASH) return ParserDefinition.SpaceRequirements.MUST_NOT
		for ((l, r) in ClojureTokens.BRACE_PAIRS)
			if (lt == l || rt == r) return ParserDefinition.SpaceRequirements.MAY
		return ParserDefinition.SpaceRequirements.MUST
	}
}

class ClojureParserUtil {
	@Suppress("UNUSED_PARAMETER")
	companion object {
		@JvmStatic
		fun adapt_builder_(root: IElementType, builder: PsiBuilder, parser: PsiParser, extendsSets: Array<TokenSet>?): PsiBuilder =
				GeneratedParserUtilBase.adapt_builder_(root, builder, parser, extendsSets)
//						.apply {
//							(this as? GeneratedParserUtilBase.Builder)?.state?.braces = null
//						}

		@JvmStatic
		fun parseTree(b: PsiBuilder, l: Int, p: GeneratedParserUtilBase.Parser) =
				GeneratedParserUtilBase.parseAsTree(GeneratedParserUtilBase.ErrorState.get(b), b, l,
						GeneratedParserUtilBase.DUMMY_BLOCK, false, p, GeneratedParserUtilBase.TRUE_CONDITION)

		@JvmStatic
		fun nospace(b: PsiBuilder, l: Int): Boolean {
			if (space(b, l)) {
				b.mark().apply { b.tokenType; error("no <whitespace> allowed") }
						.setCustomEdgeTokenBinders(WhitespacesBinders.GREEDY_LEFT_BINDER, WhitespacesBinders.GREEDY_RIGHT_BINDER)
			}
			return true
		}

		private val RECOVER_SET = TokenSet.orSet(
				ClojureTokens.SHARPS, ClojureTokens.MACROS, ClojureTokens.PAREN_ALIKE, ClojureTokens.LITERALS,
				TokenSet.create(C_DOT, C_DOTDASH))

		@JvmStatic
		fun space(b: PsiBuilder, l: Int) = b.rawLookup(0).wsOrComment() or b.rawLookup(-1).wsOrComment()

		@JvmStatic
		fun formRecover(b: PsiBuilder, l: Int) = b.tokenType !in RECOVER_SET

		@JvmStatic
		fun rootFormRecover(b: PsiBuilder, l: Int): Boolean {
			val type = b.tokenType
			return ClojureTokens.PAREN2_ALIKE.contains(type) || !RECOVER_SET.contains(type)
		}
	}
}
