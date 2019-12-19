package co.th.ford.tms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import co.th.ford.tms.dao.LoadDao;
import co.th.ford.tms.model.Load;


@Service("loadService")
@Transactional
public class LoadServiceImpl implements LoadService {

	@Autowired
	private LoadDao dao;
	
	

	public void saveLoad(Load load) {
		dao.saveLoad(load);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateLoad(Load l) {
		Load entity = dao.findLoadByID(l.getLoadID());
		if(entity!=null){
			entity.setCarrierID(l.getCarrierID());
			entity.setSystemLoadID(l.getSystemLoadID());	
			entity.setAlertTypeCode(l.getAlertTypeCode());
			entity.setLoadDescription(l.getLoadDescription());
			entity.setStatus(l.getStatus());
			entity.setErrorMessage(l.getErrorMessage());
			entity.setLastUpdateDate(l.getLastUpdateDate());
			entity.setLastUpdateUser(l.getLastUpdateUser());
		}
	}

	public void deleteLoadByID(int loadID) {
		dao.deleteLoadByID(loadID);
	}
	
	public List<Load> findAllLoads() {
		return dao.findAllLoads();
	}

	public List<Load> findLoadByCarrierID(int carrierID) {
		return dao.findLoadByCarrierID(carrierID);
	}
	
	public Load findLoadByID(int loadID) {
		return dao.findLoadByID(loadID);
	}

		
}
