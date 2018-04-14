// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CLiteral;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.intellij.clojure.devkt.lang.ClojurePsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

public class CLiteralImpl extends CSFormImpl implements CLiteral {

	public CLiteralImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitLiteral(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

	@Nullable
	public IElementType getLiteralType() {
		return ClojurePsiImplUtil.getLiteralType(this);
	}

	@NotNull
	public String getLiteralText() {
		return ClojurePsiImplUtil.getLiteralText(this);
	}

}
