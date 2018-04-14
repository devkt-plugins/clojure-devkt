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
import org.intellij.clojure.devkt.psi.CList
import org.jetbrains.kotlin.com.intellij.psi.NavigatablePsiElement
import org.jetbrains.kotlin.com.intellij.psi.PsiFile
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.com.intellij.psi.tree.ILeafElementType
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable

enum class Dialect(val coreNs: String) {
	CLJ(ClojureConstants.CLOJURE_CORE),
	CLJS(ClojureConstants.CLJS_CORE),
	CLJR(ClojureConstants.CLOJURE_CORE)
}

enum class Role {
	NONE, DEF, NS, NAME,
	RCOND, RCOND_S,
	ARG_VEC, BND_VEC, FIELD_VEC, BODY,
	ARG, BND, FIELD, PROTOTYPE
}

interface CElement : NavigatablePsiElement {
	val role: Role
	val def: IDef?
	val resolvedNs: String?
}

interface ClojureElementType
class ClojureTokenType(name: String) : IElementType(name, ClojureLanguage), ILeafElementType {
	override fun createLeafNode(leafText: CharSequence) = CToken(this, leafText)
}

class ClojureNodeType(name: String) : IElementType(name, ClojureLanguage), ClojureElementType
class CToken(tokenType: ClojureTokenType, text: CharSequence) : LeafPsiElement(tokenType, text)

interface IDef {
	val type: String
	val name: String
	val namespace: String
}

data class SymKey(
		override val name: String,
		override val namespace: String,
		override val type: String
) : IDef {
	constructor(def: IDef) : this(def.name, def.namespace, def.type)
}
