package mil.ustranscom.at21.mrv.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.UUID;

public class Requirement {
	private UUID id;
	private Date startDate;
	private String name;
	private Boolean valid;
	
	public Requirement(){}
	
	public Requirement(UUID id, Date startDate, String name){
		this.setId(id);
		this.setStartDate(startDate);
		this.setName(name);
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getValid() {
		return valid;
	}
	
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Requirement){
			Requirement that = (Requirement)o;
			
			return this.getId().equals(that.getId());

		}else{
			return false; 
		}
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
