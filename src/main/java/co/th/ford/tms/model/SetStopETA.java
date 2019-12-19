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
@Table(name="tb_setstopeta")
public class SetStopETA {
	
	public SetStopETA(){}
	
	@Id	
	@Column(name = "id", nullable = false)	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "loadStopID", nullable = false)		
	private int loadStopID;
	
	@Transient
	private LoadStop loadStop;
		
	@NotNull	
	@Column(name = "latitude", nullable = false)
	private double latitude;
	
	@NotNull
	@Column(name = "longitude", nullable = false)
	private double longitude;
	
//	@Column(name = "city", nullable = false)
//	private String city;
	
//	@Column(name = "Stage", nullable = false)
//	private String stage;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "movementDateTime", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime movementDateTime;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") 
	@Column(name = "estimatedDateTime", nullable = false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime estimatedDateTime;
	
	
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
		result = prime * result + loadStopID;
		result = prime * result + +(( estimatedDateTime== null) ? 0 : estimatedDateTime.hashCode());		
		return result;
	}	
	 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SetStopETA))
			return false;
		SetStopETA other = (SetStopETA) obj;
		if (id != other.id)
			return false;
			
		
		return true;
	}

	@Override
	public String toString() {
		return "SetStopETA [ID= "+id+",Load Stop ID=" + loadStopID  + ", Movement Date Time=" + movementDateTime 
				+", Estimated Date Time =" + estimatedDateTime + ",Latitude ="+latitude + ",Longitude ="+longitude +", Completed Flag "				
				+status +", Error Message= "+errorMessage+", Last Update Date ="+lastUpdateDate+", Last Update User ="+lastUpdateUser+"  ]";
	}
		
	
}
