// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CForm;
import org.intellij.clojure.devkt.psi.CPForm;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

import java.util.List;

public class CPFormImpl extends CFormImpl implements CPForm {

	public CPFormImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitPForm(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

	@Override
	@NotNull
	public List<CForm> getForms() {
		return PsiTreeUtil.getChildrenOfTypeAsList(this, CForm.class);
	}

}
