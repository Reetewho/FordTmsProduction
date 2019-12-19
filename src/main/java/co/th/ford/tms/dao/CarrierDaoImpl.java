package co.th.ford.tms.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Repository;

import co.th.ford.tms.model.Carrier;
import co.th.ford.tms.model.LoadListReport;
import co.th.ford.tms.model.Report1;
import co.th.ford.tms.model.PaymentReport1;


@Repository("carrierDao")
public class CarrierDaoImpl extends AbstractDao<Integer, Carrier> implements CarrierDao {

	/*public User findByUsername(String user) {
		return getByKey(user);
	}*/

	public void saveCarrier(Carrier carrier) {
		persist(carrier);
	}

	public void deleteCarrierByID(int carrierID) {
		Query query = getSession().createSQLQuery(
				  " delete tb_carrier,tb_load,tb_loadstop,tb_setstopeta "
				+ " from tb_carrier left join tb_load on tb_carrier.carrierID=tb_load.carrierID "
				+ " left join tb_loadstop on tb_load.loadID=tb_loadstop.loadID "
				+ " left join tb_setstopeta  on tb_loadstop.id=tb_setstopeta.loadStopID   "
				+ " where tb_carrier.carrierID = :carrierID ");		
		query.setInteger("carrierID", carrierID);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Carrier> findAllCarriers() {
		Criteria criteria = createEntityCriteria();
		return (List<Carrier>) criteria.list();
	}

	public Carrier findCarrierByID(int carrierID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("carrierID", carrierID));
		return (Carrier) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Carrier findCarrierByDate(String date) {
		Carrier c=null;
		Query query = getSession().createSQLQuery(
				"select * from tb_carrier where date(loadDate) = :loadDate" );
		query.setString("loadDate", date);
		List<Object[]> list=query.list();		
		if(list!=null && list.size() >0) {
			Object[] obj = list.get(0);
			c=new Carrier();
			c.setCarrierID(obj[0]==null?0:((Integer)obj[0]).intValue());
			c.setCarrierCode(obj[1]==null?"":obj[1].toString());		
			c.setLoadDate(obj[2]==null?LocalDateTime.now():sqlTimestampToJodaLocalDateTime((java.sql.Timestamp)obj[2]));		
			c.setStatus(obj[3]==null?"":obj[3].toString());
			c.setErrorMessage(obj[4]==null?"":obj[4].toString());
			c.setLastUpdateDate(obj[5]==null?LocalDateTime.now():sqlTimestampToJodaLocalDateTime((java.sql.Timestamp)obj[5]));
			c.setLastUpdateUser(obj[6]==null?"":obj[6].toString());
//			c.setStatusFlag(obj[7]==null?0:((Integer)obj[7]).intValue());
			
		}
		return c;
	}
	
	 private  LocalDateTime sqlTimestampToJodaLocalDateTime(Timestamp timestamp) {
		    return LocalDateTime.fromDateFields(timestamp);
		  }
	 
	 @SuppressWarnings("unchecked")
	public  List<Carrier> findListCarriersByDate(String startDate,String endDate){
		 Query query = getSession().createSQLQuery(
				  " select c.loadDate ,count(l.systemLoadID)as numOfLoad  "				
				+ " from tb_carrier c join tb_load l on c.carrierID=l.carrierID "				
				+ " where date(c.loadDate) >= :startDate and date(c.loadDate) <= :endtDate "
				+ " group by c.loadDate "
				+ " order by c.loadDate ");		
		query.setString("startDate", startDate);
		query.setString("endtDate", endDate);
		System.out.println("###### "+startDate+" "+endDate);
		List<Carrier> allList = new ArrayList<Carrier>();
		List<Object[]> list=query.list();
		if(list!=null && list.size() >0)
			for (Object[] obj : list) {
			
				Carrier c=new Carrier();
				c.setLoadDate(obj[0]==null?LocalDateTime.now():sqlTimestampToJodaLocalDateTime((java.sql.Timestamp)obj[0]));
				c.setNumOfLoad(obj[1]==null?0:((BigInteger)obj[1]).intValue());
				allList.add(c);
			}
		return allList;
	 }
	 
	@SuppressWarnings("unchecked")
	public List<Report1> getReport1(String startDate,String endDate){
		Query query = getSession().createSQLQuery(
				  " select l.systemLoadID,l.alertTypeCode,l.loadDescription,  "
				+"  ls.stopSequence,ls.stopShippingLocation,ls.stopShippingLocationName,ls.truckNumber,  "
				+ " ls.departureTime,ls.arriveTime,ls.completedFlag,ss.latitude,ss.longitude,ss.movementDateTime,ss.estimatedDateTime,"
				+ " l.loadStartDateTime,l.loadEndDateTime "
				+ " from tb_carrier c join tb_load l on c.carrierID=l.carrierID "
				+ " left join tb_loadstop ls on l.loadID=ls.loadID "
				+ " left join tb_setstopeta ss on ls.id=ss.loadStopID   "
				+ " where date(l.loadStartDateTime) >= :startDate and date(l.LoadEndDateTime) <= :endtDate "
				+ " order by c.carrierID ");		
		query.setString("startDate", startDate);
		query.setString("endtDate", endDate);
		System.out.println("###### "+startDate+" "+endDate);
		List<Report1> allList = new ArrayList<Report1>();
		List<Object[]> list=query.list();
		if(list!=null && list.size() >0)
			for (Object[] obj : list) {
				Report1 report1=new Report1();
				report1.setSystemLoadID(obj[0].toString());
				report1.setAlertTypeCode(obj[1].toString());
				report1.setLoadDescription(obj[2]==null?"":obj[2].toString());
				report1.setStopSequence(obj[3]==null?"":obj[3].toString());
				report1.setStopShippingLocation(obj[4]==null?"":obj[4].toString());
				report1.setStopShippingLocationName(obj[5]==null?"":obj[5].toString());
				report1.setTruckNumber(obj[6]==null?"":obj[6].toString());
				report1.setDepartureTime(obj[7]==null?"":obj[7].toString());
				report1.setArriveTime(obj[8]==null?"":obj[8].toString());				
				report1.setCompletedFlag(obj[9]==null?"":obj[9].toString());
				report1.setLatitude(obj[10]==null?"":obj[10].toString());
				report1.setLongitude(obj[11]==null?"":obj[11].toString());
				report1.setMovementDateTime(obj[12]==null?"":obj[12].toString());
				report1.setEstimatedDateTime(obj[13]==null?"":obj[13].toString());
				report1.setLoadStartDateTime(obj[14]==null?"":obj[14].toString());
				report1.setLoadEndDateTime(obj[15]==null?"":obj[15].toString());
				allList.add(report1);
			}
		
		return allList;
	}
	
	@SuppressWarnings("unchecked")
	public List<PaymentReport1> getPaymentReport1(String startDate,String endDate){
		Query query = getSession().createSQLQuery(
				  " select l.systemLoadID, l.loadDescription, ls.truckNumber, "
				+ " l.loadStartDateTime, l.loadEndDateTime, "
				+ " l.completedFlag "
				+ " from tb_carrier c join tb_load l on c.carrierID=l.carrierID "
				+ " left join tb_loadstop ls on l.loadID=ls.loadID "
				+ " left join tb_setstopeta ss on ls.id=ss.loadStopID "
//				+ " where (l.completedFlag = 'Completed') "
				+ " Where (date(l.loadStartDateTime) >= :startDate) and (date(l.LoadEndDateTime) <= :endtDate) "
				+ " group by l.systemLoadID, l.loadDescription, ls.truckNumber, l.loadStartDateTime, "
				+ " l.loadEndDateTime, l.completedFlag "
				+ " order by c.carrierID ");
//				+ " where date(l.loadStartDateTime) >= :startDate and date(l.LoadEndDateTime) <= :endtDate "	
		query.setString("startDate", startDate);
		query.setString("endtDate", endDate);
		System.out.println("###### "+startDate+" "+endDate);
		List<PaymentReport1> allList = new ArrayList<PaymentReport1>();
		List<Object[]> list=query.list();
		if(list!=null && list.size() >0)
			for (Object[] obj : list) {
				PaymentReport1 subreport1=new PaymentReport1();
				subreport1.setSystemLoadID(obj[0].toString());
				subreport1.setLoadDescription(obj[1]==null?"":obj[1].toString());
				subreport1.setTruckNumber(obj[2]==null?"":obj[2].toString());				
				subreport1.setLoadStartDateTime(obj[3]==null?"":obj[3].toString());
				subreport1.setLoadEndDateTime(obj[4]==null?"":obj[4].toString());
				subreport1.setCompletedFlag(obj[5]==null?"":obj[5].toString());
				allList.add(subreport1);
			}
		
		return allList;
	}
	
	@SuppressWarnings("unchecked")
	public List<LoadListReport> getPaymentbyLoadIDReport(String strLoadID){
		Query query = getSession().createSQLQuery(
				  " select l.loadID, l.systemLoadID, c.loadDate, l.loadDescription, ls.truckNumber, "
				+ " l.loadStartDateTime, l.loadEndDateTime, "
				+ " l.completedFlag, l.carrierID "	
				+ " from tb_carrier c join tb_load l on c.carrierID=l.carrierID "
				+ " left join tb_loadstop ls on l.loadID=ls.loadID "
				+ " left join tb_setstopeta ss on ls.id=ss.loadStopID "
				+ " where (l.systemLoadID = :strLoadID) "
				+ " group by l.loadID, l.systemLoadID, c.loadDate, l.loadDescription, ls.truckNumber, l.loadStartDateTime, "
				+ " l.loadEndDateTime, l.completedFlag, l.carrierID "
				+ " order by c.carrierID ");
		query.setString("strLoadID", strLoadID);
		System.out.println("Query Data : Find by Load ID => " + strLoadID);
		List<LoadListReport> allList = new ArrayList<LoadListReport>();
		List<Object[]> list=query.list();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
//		LocalDate strDate;
		LocalDateTime localDT;
		String strDateNow;
		
		if(list!=null && list.size()>0)
			for (Object[] obj : list) {
				LoadListReport loadlistreport1 = new LoadListReport();
				loadlistreport1.setLoadID(obj[0].toString());
				loadlistreport1.setSystemLoadID(obj[1].toString());
				
//				strDateNow = obj[2].toString(); 
//				strDate = LocalDate.parse(strDateNow, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S"));
				localDT = sqlTimestampToJodaLocalDateTime((java.sql.Timestamp)obj[2]);
				loadlistreport1.setLoadDate(localDT.toString(DateTimeFormat.forPattern("yyyy-MM-dd")));		
				
				loadlistreport1.setLoadDescription(obj[3]==null?"":obj[3].toString());
				loadlistreport1.setTruckNumber(obj[4]==null?"":obj[4].toString());				
				loadlistreport1.setLoadStartDateTime(obj[5]==null?"":obj[5].toString());
				loadlistreport1.setLoadEndDateTime(obj[6]==null?"":obj[6].toString());
				loadlistreport1.setCompletedFlag(obj[7]==null?"":obj[7].toString());
				loadlistreport1.setCarrierID(obj[8]==null?"":obj[8].toString());
				
				
				if(allList!=null && allList.size()>0) {
					for (LoadListReport dataLoadList : allList) {
						String strSystemLoadID = dataLoadList.getSystemLoadID();
					    if (loadlistreport1.getSystemLoadID().equalsIgnoreCase(strSystemLoadID)) {
					    	if(loadlistreport1.getCompletedFlag().equalsIgnoreCase("Completed")) {
					    		allList.remove(dataLoadList);
					    		allList.add(loadlistreport1);
					    	}else if(loadlistreport1.getCompletedFlag().equalsIgnoreCase("In transit")) {
					    		if(dataLoadList.getCompletedFlag().equalsIgnoreCase("In transit") 
					    		|| dataLoadList.getCompletedFlag().equalsIgnoreCase("Load") 
					    		|| dataLoadList.getCompletedFlag().equalsIgnoreCase("N/A")) {
					    			allList.remove(dataLoadList);
						    		allList.add(loadlistreport1);
					    		}
					    	}else if(loadlistreport1.getCompletedFlag().equalsIgnoreCase("Load")) {
					    		if(dataLoadList.getCompletedFlag().equalsIgnoreCase("Load") 
					    		|| dataLoadList.getCompletedFlag().equalsIgnoreCase("N/A")) {
					    			allList.remove(dataLoadList);
						    		allList.add(loadlistreport1);
					    		}
					    	}else if(loadlistreport1.getCompletedFlag().equalsIgnoreCase("N/A")) {
					    		if(dataLoadList.getCompletedFlag().equalsIgnoreCase("N/A")) {
					    			allList.remove(dataLoadList);
						    		allList.add(loadlistreport1);
					    		}
					    	}
					        
					    }
					}
				}else {
					allList.add(loadlistreport1);
				}
				
				
				
				
			}
		
		return allList;
	}
}
