// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CRegexp;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

public class CRegexpImpl extends CLiteralImpl implements CRegexp {

	public CRegexpImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitRegexp(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

}
