package co.th.ford.tms.webservice;



import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;

import org.joda.time.LocalDateTime;

import co.th.ford.tms.model.Load;
import co.th.ford.tms.model.LoadStop;
import co.th.ford.tms.webservice.Base;


public class ProcessLoadRetrieve extends Base{
		
	private  final String wsEndpoint = "https://fordswsqa.jdadelivers.com/webservices/services/TransportationManager2";

	private  final String inputXML = 
					"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cis=\"http://www.i2.com/cis\"> \r\n" + 
					"   <soapenv:Header /> \r\n" + 
					"   <soapenv:Body> \r\n" + 
					"      <cis:processLoadRetrieve>\r\n" + 
					"			<ApiHeader> \r\n" + 
					"				<OperationName>processLoadRetrieve</OperationName>\r\n" + 
					"				<ClientID>CallCode</ClientID>\r\n" + 
					"			</ApiHeader>\r\n" + 
					"         	<SystemLoadID>%s</SystemLoadID>\r\n" + 
					" 			<Page>\r\n" + 
					"  				<MaxRows>9999999</MaxRows> \r\n" + 
					" 			</Page>		   \r\n" + 
					"		</cis:processLoadRetrieve>\r\n" + 
					"   </soapenv:Body> \r\n" + 
					"</soapenv:Envelope>";
	
	public Load submit(String Authenticationm,String SOAPAction,Load loadModel){
		
		try{			
			SOAPBody sb = getResponse(sendRequest(wsEndpoint,Authenticationm,SOAPAction,String.format(inputXML, ""+loadModel.getSystemLoadID())));
			loadModel.setStatus(extract(sb,"ns1:CompletedSuccessfully"));			
			if(loadModel.getStatus().equals("true") ) {
				List<LoadStop> lsList=new ArrayList<LoadStop>();
				loadModel.setLoadDescription(extract(sb,"ns1:LoadDescription"));
				if (sb.getElementsByTagName("ns1:Stop").getLength() > 0) {
					for(int i =0;i<sb.getElementsByTagName("ns1:Stop").getLength();i++ ){
						LoadStop lsModel=new LoadStop();
						lsModel.setLoadID(loadModel.getLoadID());
						lsModel.setLoad(loadModel);
						lsModel.setStopSequence(Integer.parseInt(sb.getElementsByTagName("ns1:StopSequenceNumber").item(i).getFirstChild().getNodeValue()));
						lsModel.setStopShippingLocation(sb.getElementsByTagName("ns1:ShippingLocationCode").item(i).getFirstChild().getNodeValue());
						lsModel.setStopShippingLocationName(sb.getElementsByTagName("ns1:ShippingLocationName").item(i).getFirstChild().getNodeValue());
						lsModel.setArriveTime(LocalDateTime.parse(sb.getElementsByTagName("ns1:AppointmentDependentDockCommitmentStartDateTime").item(i).getFirstChild().getNodeValue()));
						lsModel.setDepartureTime(LocalDateTime.parse(sb.getElementsByTagName("ns1:AppointmentDependentDockCommitmentEndDateTime").item(i).getFirstChild().getNodeValue()));
						lsModel.setStatus("load");
						lsList.add(lsModel);						
					}					
				}
				loadModel.setLoadStopList(lsList);
			}else{
				loadModel.setErrorMessage(extract(sb,"ns1:SystemMessage"));
			}
					
		}catch(Exception e){
			e.printStackTrace();
		}
		return loadModel;
	}
}
