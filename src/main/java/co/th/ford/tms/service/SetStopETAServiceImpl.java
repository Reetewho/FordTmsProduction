package co.th.ford.tms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import co.th.ford.tms.dao.SetStopETADao;
import co.th.ford.tms.model.SetStopETA;


@Service("setStopETAService")
@Transactional
public class SetStopETAServiceImpl implements SetStopETAService {

	@Autowired
	private SetStopETADao dao;
	
	

	public void saveSetStopETA(SetStopETA setStopETA) {
		dao.saveSetStopETA(setStopETA);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateSetStopETA(SetStopETA sse) {
		SetStopETA entity = dao.findSetStopETAByID(sse.getId());
		if(entity!=null){
			entity.setLoadStopID(sse.getLoadStopID());
			entity.setMovementDateTime(sse.getEstimatedDateTime());
			entity.setEstimatedDateTime(sse.getEstimatedDateTime());
			entity.setLatitude(sse.getLatitude());
			entity.setLongitude(sse.getLongitude());			
			entity.setStatus(sse.getStatus());
			entity.setErrorMessage(sse.getErrorMessage());
			entity.setLastUpdateDate(sse.getLastUpdateDate());
			entity.setLastUpdateUser(sse.getLastUpdateUser());
		}
	}

	public void deleteSetStopETAByID(int setStopETAID) {
		dao.deleteSetStopETAByID(setStopETAID);
	}
	
	public List<SetStopETA> findAllSetStopETAs() {
		return dao.findAllSetStopETAs();
	}

	public SetStopETA findSetStopETAByLoadStopID(int loadStopID) {
		return dao.findSetStopETAByLoadStopID(loadStopID);
	}
	
	public SetStopETA findSetStopETAByID(int setStopETAID) {
		return dao.findSetStopETAByID(setStopETAID);
	}

		
}
