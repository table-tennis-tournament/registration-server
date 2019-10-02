package de.tt.treg.server.controller.preparator;

import java.util.List;

public interface IPreparator<T> {

	List<T> prepare(List<T> typesToPrepare);

	void setSinglePreparators(List<ISinglePreparator<T>> preparators);

}
