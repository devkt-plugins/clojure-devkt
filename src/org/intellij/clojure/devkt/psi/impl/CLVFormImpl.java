// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CLVForm;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

public class CLVFormImpl extends CPFormImpl implements CLVForm {

	public CLVFormImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitLVForm(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

}
