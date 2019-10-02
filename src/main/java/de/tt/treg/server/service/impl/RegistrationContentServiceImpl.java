package de.tt.treg.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tt.treg.server.dao.RegistrationContentDao;
import de.tt.treg.server.domain.RegistrationContent;
import de.tt.treg.server.service.RegistrationContentService;

@Service("registrationContentService")
@Transactional(readOnly = true)
public class RegistrationContentServiceImpl implements RegistrationContentService{

	@Autowired
	private RegistrationContentDao registrationContentDao;
	
	@Override
	@Transactional(readOnly = false)
	public RegistrationContent getRegistrationInformation() {
		return this.registrationContentDao.findById(1);
	}

}
