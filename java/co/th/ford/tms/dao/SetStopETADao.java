package co.th.ford.tms.dao;

import java.util.List;


import co.th.ford.tms.model.SetStopETA;


public interface SetStopETADao {

	SetStopETA findSetStopETAByID(int setStopETAID);

	void saveSetStopETA(SetStopETA setStopETA);
	
	void deleteSetStopETAByID(int setStopETAID);
	
	SetStopETA findSetStopETAByLoadStopID(int loadStopID);
	
	List<SetStopETA> findAllSetStopETAs();

	

}
