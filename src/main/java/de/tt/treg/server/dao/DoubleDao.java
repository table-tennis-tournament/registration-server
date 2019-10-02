package de.tt.treg.server.dao;

import java.util.List;

import de.tt.treg.server.domain.TTDouble;

public interface DoubleDao extends AbstractDao<TTDouble, Integer> {

	List<TTDouble> getDoublesByUserId(int userId);

}
