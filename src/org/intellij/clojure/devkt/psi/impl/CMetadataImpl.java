// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CForm;
import org.intellij.clojure.devkt.psi.CMetadata;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.intellij.clojure.devkt.lang.CComposite;
import org.intellij.clojure.devkt.lang.ClojurePsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

public class CMetadataImpl extends CComposite implements CMetadata {

	public CMetadataImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitMetadata(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

	@Override
	@Nullable
	public CForm getForm() {
		return PsiTreeUtil.getChildOfType(this, CForm.class);
	}

	@NotNull
	public String toString() {
		return ClojurePsiImplUtil.toString(this);
	}

}
