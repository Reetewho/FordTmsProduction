package co.th.ford.tms.dao;

import java.util.List;


import co.th.ford.tms.model.LoadStop;


public interface LoadStopDao {

	LoadStop findLoadStopByID(int loadStopID);

	void saveLoadStop(LoadStop loadStop);
	
	void deleteLoadStopByID(int loadStopID);	
	
	List<LoadStop> findLoadStopByLoadID(int loadID);
	
	List<LoadStop> findAllLoadStops();

	 List<LoadStop> findNotCompletedStatusByLoadID(int loadID);

}
