package sddc.services.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class OrderedService {
	@Id @GeneratedValue
	private long id;
	
	@Column(name="name")
	private String name;
	
	@ElementCollection(targetClass=String.class,fetch = FetchType.EAGER)
	private List<String> identifiers = new ArrayList<String>();
	
	
	public OrderedService () {}
	
	public OrderedService (String name, List<String> ids) {
		this.name = name;
		this.identifiers = ids;
	}
	
	

	public List<String> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}

	public String getOrdredServiceName() {
		return name;
	}

	public void setOrdredServiceName(String name) {
		this.name = name;
	}
	
	
}
