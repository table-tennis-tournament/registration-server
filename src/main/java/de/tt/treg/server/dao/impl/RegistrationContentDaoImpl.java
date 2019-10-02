package de.tt.treg.server.dao.impl;

import org.springframework.stereotype.Repository;

import de.tt.treg.server.dao.RegistrationContentDao;
import de.tt.treg.server.domain.RegistrationContent;

@Repository
public class RegistrationContentDaoImpl extends AbstractDaoImpl<RegistrationContent, Integer> implements RegistrationContentDao{

	public RegistrationContentDaoImpl() {
		super(RegistrationContent.class);
	}


	@Override
	protected String getTableName() {
		return "registrationContent";
	}

}
