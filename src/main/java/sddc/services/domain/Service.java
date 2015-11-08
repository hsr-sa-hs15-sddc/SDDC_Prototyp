package sddc.services.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

import javax.persistence.JoinColumn;




@Entity
@Table(name = "service")  
public class Service {
		
	@Id @GeneratedValue
	private long id;
	private String serviceName;
	private String image;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "service_modules", joinColumns = { 
			@JoinColumn(name = "service_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "servicemodule_id", 
					nullable = true, updatable = true) })
	private Set<ServiceModule> modules = new HashSet<ServiceModule>(0);
	
	public Service () {}
	
	public Service(String serviceName, String image, Set<ServiceModule> modules) {
	    this.serviceName = serviceName;
	    this.image = image;
	    this.modules = modules;
	}
	
	public Service(String serviceName, String image) {
	    this.serviceName = serviceName;
	    this.image = image;
	}

	public long getId() {
		return id;
	}

	public String getServiceName() {
		return serviceName;
	}
	
	public String getImage() {
		return image;
	}
	
	
	
	public Set<ServiceModule> getServiceModules() {
		return this.modules;
	}
	
	public void setServiceModules(Set<ServiceModule> modules) {
		this.modules = modules;
	}
}
