package co.th.ford.tms.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;


import org.hibernate.annotations.Type;

import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="tb_loadstop")
public class LoadStop {
	
	public LoadStop(){}
	
	@Id	
	@Column(name = "id", nullable = false)	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
		
	@Column(name = "loadID", nullable = false)
	private int loadID;
	
	@Transient
	private Load load;
	
	
	@Column(name = "stopSequence", nullable = false)
	private int stopSequence;	
	
	
	@Column(name = "stopShippingLocation", nullable = false)
	private String stopShippingLocation;
	
	@Column(name = "stopShippingLocationName", nullable = false)
	private String stopShippingLocationName;
	
		
	@Column(name = "truckNumber", nullable = false)
	private String truckNumber;
		
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "departureTime", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime departureTime;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "arriveTime", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime arriveTime;
		
	@Column(name = "completedFlag", nullable = false)
	private String status;	
	
	@Column(name = "errorMessage", nullable = true)
	private String errorMessage;
	
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "lastUpdateDate", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime lastUpdateDate;	
	
	@Column(name = "lastUpdateUser", nullable = true)
	private String lastUpdateUser;

	@Override
	public int hashCode() {		
		final int prime = 31;
		int result = 1;
		result = prime * result + id;		
		result = prime * result + stopSequence;
		result = prime * result +((stopShippingLocation == null) ? 0 : stopShippingLocation.hashCode());
		return result;
	}	
	 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LoadStop))
			return false;
		LoadStop other = (LoadStop) obj;
		if (id != other.id)
			return false;
			
		
		return true;
	}

	@Override
	public String toString() {
		return "LoadStop [,ID= "+id+",Load ID=" + loadID+", Stop Sequense =" + stopSequence 
				+ ", Stop Shipping Location =" + stopShippingLocation + ", Truck Number=" + truckNumber
				+ ", Departure Time =" + departureTime  + ",arriveTime =" + arriveTime
				+ ", Completed Flag =" + status + ", Error Message= " + errorMessage
				+ ", Last Update Date ="+lastUpdateDate+", Last Update User =" + lastUpdateUser + "]";
//				+ ", Status Flag =" + statusFlag + "]";
	}
		
	
}
