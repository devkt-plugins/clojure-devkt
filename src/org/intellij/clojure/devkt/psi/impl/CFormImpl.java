// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi.impl;

import org.intellij.clojure.devkt.psi.CForm;
import org.intellij.clojure.devkt.psi.CMetadata;
import org.intellij.clojure.devkt.psi.CReaderMacro;
import org.intellij.clojure.devkt.psi.ClojureVisitor;
import org.intellij.clojure.devkt.lang.CComposite;
import org.intellij.clojure.devkt.lang.ClojurePsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

import java.util.List;

public class CFormImpl extends CComposite implements CForm {

	public CFormImpl(IElementType type) {
		super(type);
	}

	public void accept(@NotNull ClojureVisitor visitor) {
		visitor.visitForm(this);
	}

	public void accept(@NotNull PsiElementVisitor visitor) {
		if (visitor instanceof ClojureVisitor) accept((ClojureVisitor) visitor);
		else super.accept(visitor);
	}

	@Override
	@NotNull
	public List<CMetadata> getMetas() {
		return PsiTreeUtil.getChildrenOfTypeAsList(this, CMetadata.class);
	}

	@Override
	@NotNull
	public List<CReaderMacro> getReaderMacros() {
		return PsiTreeUtil.getChildrenOfTypeAsList(this, CReaderMacro.class);
	}

	@NotNull
	public String toString() {
		return ClojurePsiImplUtil.toString(this);
	}

}
