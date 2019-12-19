package co.th.ford.tms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.th.ford.tms.dao.CarrierDao;
import co.th.ford.tms.model.Carrier;


@Service("carrierService")
@Transactional
public class CarrierServiceImpl implements CarrierService {

	@Autowired
	private CarrierDao dao;
	
	

	public void saveCarrier(Carrier carrier) {
		dao.saveCarrier(carrier);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateCarrier(Carrier c) {
		Carrier entity = dao.findCarrierByID(c.getCarrierID());
		if(entity!=null){
			entity.setCarrierCode(c.getCarrierCode());
			entity.setLoadDate(c.getLoadDate());
			entity.setStatus(c.getStatus());
			entity.setErrorMessage(c.getErrorMessage());
			entity.setLastUpdateDate(c.getLastUpdateDate());
			entity.setLastUpdateUser(c.getLastUpdateUser());
		}
	}

	public void deleteCarrierByID(int carrierID) {
		dao.deleteCarrierByID(carrierID);
	}
	
	public List<Carrier> findAllCarriers() {
		return dao.findAllCarriers();
	}

	public Carrier findCarrierByID(int carrierID) {
		return dao.findCarrierByID(carrierID);
	}

		
}
