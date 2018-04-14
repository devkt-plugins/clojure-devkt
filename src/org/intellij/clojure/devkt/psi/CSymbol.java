// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedReference;

public interface CSymbol extends CSForm {

	@NotNull
	String getName();

	@NotNull
	String getQualifiedName();

	@Nullable
	CSymbol getQualifier();

	int getTextOffset();
}
