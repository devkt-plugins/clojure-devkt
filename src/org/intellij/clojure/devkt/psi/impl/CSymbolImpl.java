// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CSymbol;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.intellij.clojure.devkt.lang.ClojurePsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

public class CSymbolImpl extends CSFormImpl implements CSymbol {

	public CSymbolImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitSymbol(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

	@NotNull
	public String getName() {
		return ClojurePsiImplUtil.getName(this);
	}

	@NotNull
	public String getQualifiedName() {
		return ClojurePsiImplUtil.getQualifiedName(this);
	}

	@Nullable
	public CSymbol getQualifier() {
		return ClojurePsiImplUtil.getQualifier(this);
	}

	public int getTextOffset() {
		return ClojurePsiImplUtil.getTextOffset(this);
	}
}
