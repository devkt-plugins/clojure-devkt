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

import org.intellij.clojure.devkt.psi.*
import org.intellij.clojure.devkt.psi.impl.CFormImpl
import org.intellij.clojure.devkt.psi.impl.CLVFormImpl
import org.intellij.clojure.devkt.util.*
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil
import org.jetbrains.kotlin.com.intellij.psi.*
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore

/**
 * @author gregsh
 */
object ClojurePsiImplUtil {
	@JvmStatic
	fun toString(o: PsiElement) = "${StringUtil.getShortName(o::class.java.toString())}(${PsiUtilCore.getElementType(o)})"

	@JvmStatic
	fun getName(o: CSymbol): String = o.lastChild.text

	@JvmStatic
	fun getTextOffset(o: CSymbol): Int = o.lastChild.textRange.startOffset

	@JvmStatic
	fun getQualifier(o: CSymbol): CSymbol? = o.lastChild.findPrev(CSymbol::class)

	@JvmStatic
	fun getQualifiedName(o: CSymbol): String {
		val offset = o.qualifier?.textRange?.startOffset ?: o.findChild(CToken::class)!!.textRange.startOffset
		val delta = if (o.lastChild.node.elementType == ClojureTypes.C_DOT) -1 else 0
		return o.text.let { it.substring(offset - o.textRange.startOffset, it.length + delta) }
	}

	@JvmStatic
	fun getName(o: CKeywordBase): String = o.symbol.name

	@JvmStatic
	fun getNamespace(o: CKeywordBase): String = o.resolvedNs!!

	@JvmStatic
	fun getTextOffset(o: CKeywordBase): Int = o.symbol.textOffset

	@JvmStatic
	fun getTextOffset(o: CListBase): Int =
			(o.findChild(Role.NAME) ?: o.firstForm)?.textOffset
					?: o.textRange.startOffset

	@JvmStatic
	fun getFirst(o: CList): CSymbol? = o.findChild(CForm::class) as? CSymbol

	@JvmStatic
	fun getLiteralType(o: CLiteral): IElementType? = o.lastChild?.elementType

	@JvmStatic
	fun getLiteralText(o: CLiteral): String = o.lastChild?.text ?: ""
}

open class CComposite(tokenType: IElementType) : CompositePsiElement(tokenType), CElement {
	override val role: Role get() = role(data)
	override val def: IDef? get() = data as? IDef
	override val resolvedNs: String? get() = data as? String

	internal val roleImpl: Role get() = role(dataImpl)
	@JvmField
	internal var dataImpl: Any? = null
	private val data: Any get() = Role.NONE
}

abstract class CListBase(nodeType: IElementType) : CLVFormImpl(nodeType), CList

abstract class CKeywordBase(nodeType: IElementType) : CFormImpl(nodeType), CKeyword,
		PsiNameIdentifierOwner, PsiQualifiedNamedElement {

	abstract override fun getName(): String

	override fun getNameIdentifier() = lastChild!!
	override fun setName(newName: String): PsiElement {
		nameIdentifier.replace(newLeafPsiElement(project, newName))
		return this
	}

	override fun getQualifiedName() = name.withNamespace(namespace)
}

fun newLeafPsiElement(project: Project, s: String): PsiElement =
		PsiFileFactory.getInstance(project).createFileFromText(ClojureLanguage, s).firstChild.lastChild

private fun role(data: Any?): Role {
	return when (data) {
		is Role -> data
		is Imports, is NSDef -> Role.NS
		is IDef -> Role.DEF
		else -> Role.NONE
	}
}
