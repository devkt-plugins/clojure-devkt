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

import org.intellij.clojure.devkt.psi.CSymbol
import org.jetbrains.kotlin.com.intellij.extapi.psi.PsiFileBase
import org.jetbrains.kotlin.com.intellij.lang.Language
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider

class CFileImpl(viewProvider: FileViewProvider, language: Language) :
		PsiFileBase(viewProvider, language) {

	override fun getFileType() = ClojureFileType
	override fun toString() = "${javaClass.simpleName}:$name"
}

internal class NSDef(
		val key: SymKey
) : IDef by key

internal data class Imports(
		val imports: List<Import>,
		val dialect: Dialect,
		val range: TextRange,
		val scopeEnd: Int)

internal data class Import(
		val nsType: String,
		val namespace: String,
		val alias: String,
		val aliasSym: CSymbol?,
		val refer: Set<String> = emptySet(),
		val only: Set<String> = emptySet(),
		val exclude: Set<String> = emptySet(),
		val rename: Map<String, Any> = emptyMap())
