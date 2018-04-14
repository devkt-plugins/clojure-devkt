// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CReaderMacro;
import org.intellij.clojure.devkt.psi.CSymbol;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.intellij.clojure.devkt.lang.CComposite;
import org.intellij.clojure.devkt.lang.ClojurePsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

public class CReaderMacroImpl extends CComposite implements CReaderMacro {

	public CReaderMacroImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitReaderMacro(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

	@Override
	@Nullable
	public CSymbol getSymbol() {
		return PsiTreeUtil.getChildOfType(this, CSymbol.class);
	}

	@NotNull
	public String toString() {
		return ClojurePsiImplUtil.toString(this);
	}

}
