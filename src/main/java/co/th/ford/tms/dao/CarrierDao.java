package co.th.ford.tms.dao;

import java.util.List;

import co.th.ford.tms.model.Carrier;
import co.th.ford.tms.model.LoadListReport;
import co.th.ford.tms.model.PaymentReport1;
import co.th.ford.tms.model.Report1;



public interface CarrierDao {

	Carrier findCarrierByID(int carrierID);

	void saveCarrier(Carrier carrier);
	
	void deleteCarrierByID(int carrierID);
	
	List<Carrier> findAllCarriers();

	List<Report1> getReport1(String startDate,String endDate);
	
	Carrier findCarrierByDate(String date);
	
	List<Carrier> findListCarriersByDate(String startDate,String endDate);
	
	List<PaymentReport1> getPaymentReport1(String startDate,String endDate);
	
	List<LoadListReport> getPaymentbyLoadIDReport(String strLoadID);
}
