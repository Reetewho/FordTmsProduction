package co.th.ford.tms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import co.th.ford.tms.dao.LoadStopDao;
import co.th.ford.tms.model.LoadStop;


@Service("loadStopService")
@Transactional
public class LoadStopServiceImpl implements LoadStopService {

	@Autowired
	private LoadStopDao dao;
	
	

	public void saveLoadStop(LoadStop loadStop) {
		dao.saveLoadStop(loadStop);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateLoadStop(LoadStop ls) {
		LoadStop entity = dao.findLoadStopByID(ls.getId());
		if(entity!=null){
			entity.setLoadID(ls.getLoadID());
			entity.setStopSequence(ls.getStopSequence());
			entity.setStopShippingLocation(ls.getStopShippingLocation());
			entity.setStopShippingLocationName(ls.getStopShippingLocationName());
			entity.setTruckNumber(ls.getTruckNumber());
			entity.setDepartureTime(ls.getDepartureTime());
			entity.setArriveTime(ls.getArriveTime());
			
			//----------------------------------------------------------------
//			entity.setShipingOrder(ls.getShipingOrder());
//			entity.setWaybillNumber(ls.getWaybillNumber());
//			entity.setManifest(ls.getManifest());
//			entity.setLoadstopremark(ls.getLoadstopremark());
			//----------------------------------------------------------------
//			entity.setStatusLoad(ls.getStatusLoad());
			entity.setStatus(ls.getStatus());
			entity.setErrorMessage(ls.getErrorMessage());
			entity.setLastUpdateDate(ls.getLastUpdateDate());
			entity.setLastUpdateUser(ls.getLastUpdateUser());
		}
	}

	public void deleteLoadStopByID(int loadStopID) {
		dao.deleteLoadStopByID(loadStopID);
	}
	
	public List<LoadStop> findAllLoadStops() {
		return dao.findAllLoadStops();
	}

	public List<LoadStop> findLoadStopByLoadID(int loadID) {
		return dao.findLoadStopByLoadID(loadID);
	}
	
	public LoadStop findLoadStopByID(int loadStopID) {
		return dao.findLoadStopByID(loadStopID);
	}

	public  List<LoadStop> findNotCompletedStatusByLoadID(int loadID){
		return dao.findNotCompletedStatusByLoadID(loadID);
	}	
}
