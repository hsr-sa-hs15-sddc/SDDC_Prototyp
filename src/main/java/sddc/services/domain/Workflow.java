package sddc.services.domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import org.libvirt.LibvirtException;

import sddc.services.genericapi.IGenericAPIFacade;

public class Workflow {
	
	private IGenericAPIFacade api;
	
	private Set<Identifier> ids = new HashSet();
	
    public static final Logger LOGGER = LoggerFactory.getLogger(Workflow.class);

	
	//TODO: DI and Factory
	public Workflow(IGenericAPIFacade api) {
		this.api = api;
		try {
			api.connect("test:///default", false);
		} catch (LibvirtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Refactoring + Logging
	public void orderService(Service service) {
		for(ServiceModule serviceModule : service.getServiceModules(Category.Network)) {
			try {
				api.createNetwork(serviceModule.getConfig());
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Network: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		for(ServiceModule serviceModule : service.getServiceModules(Category.Storage)) {
			try {
				api.createStorage(serviceModule.getConfig());
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Storage: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		for(ServiceModule serviceModule : service.getServiceModules(Category.Compute)) {
			try {
				api.createCompute(serviceModule.getConfig());
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Compute: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		//Persistance
	}
	
	//Refactoring + Logging
	public void cancelService(OrderedService orderedService) {
		for(Identifier identifier : orderedService.getIdentifiers(Category.Compute)) {
			try {
				api.deleteCompute(identifier.getUuid());
			} catch (LibvirtException e) {
				LOGGER.error("Could not delete Compute: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		for(Identifier identifier : orderedService.getIdentifiers(Category.Storage)) {
			try {
				api.deleteStorage(identifier.getUuid());
			} catch (LibvirtException e) {
				LOGGER.error("Could not delete Storage: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		for(Identifier identifier : orderedService.getIdentifiers(Category.Network)) {
			try {
				api.deleteNetwork(identifier.getUuid());
			} catch (LibvirtException e) {
				LOGGER.error("Could not delete Network: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	

}
