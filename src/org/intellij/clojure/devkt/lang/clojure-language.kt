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

import org.intellij.clojure.devkt.ClojureConstants
import org.intellij.clojure.devkt.ClojureIcons
import org.intellij.clojure.devkt.psi.ClojureTypes.*
import org.jetbrains.kotlin.com.intellij.lang.Language
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.LanguageFileType
import org.jetbrains.kotlin.com.intellij.psi.TokenType
import org.jetbrains.kotlin.com.intellij.psi.tree.*

/**
 * @author gregsh
 */
object ClojureFileType : LanguageFileType(ClojureLanguage) {
	override fun getIcon() = ClojureIcons.CLOJURE_ICON
	override fun getName() = "Clojure"
	override fun getDefaultExtension() = ClojureConstants.CLJ
	override fun getDescription() = "Clojure and ClojureScript"
}

object ClojureLanguage : Language("Clojure") {
	override fun getAssociatedFileType() = ClojureFileType
}

object ClojureScriptLanguage : Language(ClojureLanguage, "ClojureScript")

object ClojureTokens {
	@JvmField
	val CLJ_FILE_TYPE = IFileElementType("CLOJURE_FILE", ClojureLanguage)
	@JvmField
	val CLJS_FILE_TYPE = IFileElementType("CLOJURE_SCRIPT_FILE", ClojureScriptLanguage)

	@JvmField
	val LINE_COMMENT = IElementType("C_LINE_COMMENT", ClojureLanguage)

	@JvmField
	val WHITESPACES = TokenSet.create(C_COMMA, TokenType.WHITE_SPACE)
	@JvmField
	val COMMENTS = TokenSet.create(LINE_COMMENT)
	@JvmField
	val STRINGS = TokenSet.create(C_STRING, C_STRING_UNCLOSED)
	@JvmField
	val LITERALS = TokenSet.create(C_BOOL, C_CHAR, C_HEXNUM, C_NIL, C_NUMBER, C_RATIO, C_RDXNUM, C_STRING, C_SYM)

	@JvmField
	val SHARPS = TokenSet.create(C_SHARP, C_SHARP_COMMENT, C_SHARP_QMARK, C_SHARP_QMARK_AT, C_SHARP_EQ, C_SHARP_HAT,
			C_SHARP_QUOTE, C_SHARP_NS, C_SHARP_SYM)
	@JvmField
	val MACROS = TokenSet.create(C_AT, C_COLON, C_COLONCOLON, C_HAT, C_QUOTE, C_SYNTAX_QUOTE, C_TILDE, C_TILDE_AT)

	@JvmField
	val PAREN1_ALIKE = TokenSet.create(C_PAREN1, C_BRACE1, C_BRACKET1)
	@JvmField
	val PAREN2_ALIKE = TokenSet.create(C_PAREN2, C_BRACE2, C_BRACKET2)
	@JvmField
	val PAREN_ALIKE = TokenSet.orSet(PAREN1_ALIKE, PAREN2_ALIKE)
	@JvmField
	val LIST_ALIKE = TokenSet.create(C_FUN, C_LIST, C_MAP, C_SET, C_VEC)

	@JvmField
	val FORMS = TokenSet.create(C_CONSTRUCTOR, C_FORM, C_FUN, C_KEYWORD,
			C_LIST, C_LITERAL, C_MAP, C_REGEXP,
			C_SET, C_SYMBOL, C_VEC)

	@JvmField
	val BRACE_PAIRS = listOf(
			Pair(C_PAREN1, C_PAREN2),
			Pair(C_BRACE1, C_BRACE2),
			Pair(C_BRACKET1, C_BRACKET2))
}
