// This is a generated file. Not intended for manual editing.
package org.intellij.clojure.devkt.psi;

import org.intellij.clojure.devkt.lang.CElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CForm extends CElement {

	@NotNull
	List<CMetadata> getMetas();

	@NotNull
	List<CReaderMacro> getReaderMacros();

}
