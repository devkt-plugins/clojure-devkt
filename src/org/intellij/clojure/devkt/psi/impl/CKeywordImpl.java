// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CKeyword;
import org.intellij.clojure.devkt.psi.CSymbol;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.intellij.clojure.devkt.lang.CKeywordBase;
import org.intellij.clojure.devkt.lang.ClojurePsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

public class CKeywordImpl extends CKeywordBase implements CKeyword {

	public CKeywordImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitKeyword(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

	@Override
	@NotNull
	public CSymbol getSymbol() {
		return PsiTreeUtil.getChildOfType(this, CSymbol.class);
	}

	@NotNull
	public String getName() {
		return ClojurePsiImplUtil.getName(this);
	}

	@NotNull
	public String getNamespace() {
		return ClojurePsiImplUtil.getNamespace(this);
	}

	public int getTextOffset() {
		return ClojurePsiImplUtil.getTextOffset(this);
	}

}
