package co.th.ford.tms.webservice;



import javax.xml.soap.SOAPBody;

import co.th.ford.tms.model.LoadStop;
import co.th.ford.tms.webservice.Base;


public class ProcessLoadStatusUpdate extends Base{
		
	private  final String wsEndpoint = "https://fordswsprd.jdadelivers.com/webservices/services/TransportationManager2";

	private  final String inputXML = 
			        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cis=\"http://www.i2.com/cis\"> \r\n" +					 
					"   <soapenv:Header/>\r\n" + 
					"   <soapenv:Body>\r\n" + 
					"      <cis:processLoadStatusUpdate>      \r\n" + 
					"         <cis:ApiHeader>\r\n" + 
					"            <cis:OperationName>processLoadStatusUpdate</cis:OperationName>\r\n" + 
					"         </cis:ApiHeader>\r\n" + 
					"         <cis:LoadStatusUpdateData>      \r\n" + 
					"            <cis:SystemLoadID>%s</cis:SystemLoadID>\r\n" + 
					"            <cis:TrailerNumber>%s</cis:TrailerNumber>          \r\n" + 
					"            <cis:StopArrivalDepartureData>\r\n" + 
					"               <cis:ShippingLocationCode>%s</cis:ShippingLocationCode>\r\n" + 
					"               <cis:StopTypeEnumVal>STR_NULL</cis:StopTypeEnumVal>\r\n" + 
					"               <cis:ArrivalDateTime>%s</cis:ArrivalDateTime>\r\n" + 
					"              <cis:ArrivalEventCode>DRVRCHKIN_</cis:ArrivalEventCode>\r\n" + 
					"               <cis:DepartureDateTime>%s</cis:DepartureDateTime>\r\n" + 
					"               <cis:DepartureEventCode>DRVRCHKOUT_</cis:DepartureEventCode>\r\n" + 
					"               <cis:UpdateStatusFlag>true</cis:UpdateStatusFlag>\r\n" + 
					"            </cis:StopArrivalDepartureData>\r\n" + 
					"         </cis:LoadStatusUpdateData>        \r\n" + 
					"      </cis:processLoadStatusUpdate>\r\n" + 
					"   </soapenv:Body>\r\n" + 
					"</soapenv:Envelope>";
	
	public LoadStop submit(String Authenticationm,String SOAPAction,LoadStop lsModel){		
		try{			
			SOAPBody sb = getResponse(sendRequest(wsEndpoint,Authenticationm,SOAPAction,String.format(inputXML, ""+lsModel.getLoad().getSystemLoadID(),lsModel.getTruckNumber(),lsModel.getStopShippingLocation(),lsModel.getArriveTime(),lsModel.getDepartureTime())));
			lsModel.setStatus(extract(sb,"ns1:CompletedSuccessfully"));
			if(!lsModel.getStatus().equals("true")) {				
				lsModel.setErrorMessage(extract(sb,"ns1:SystemMessage"));
			}
					
		}catch(Exception e){
			e.printStackTrace();
		}
		return lsModel;
	}
}
