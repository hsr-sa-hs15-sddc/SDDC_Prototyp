package sddc.services.domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import java.util.HashSet;
import java.util.Set;

import org.libvirt.LibvirtException;


import sddc.services.OrderedServiceRepo;
import sddc.services.genericapi.GenericAPILibVirt;
import sddc.services.genericapi.IGenericAPIFacade;

@Controller
public class Workflow {

	@Autowired
	private OrderedServiceRepo orderedServiceRepo;
	
	private IGenericAPIFacade api;
	
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
	
	public Workflow() {
		this(new GenericAPILibVirt());
	}
	
	//Refactoring + Logging
	public void orderService(Service service) {
		
		Set<Identifier> ids = new HashSet<Identifier>();
		System.out.println(service.getServiceName());
		for(ServiceModule serviceModule : service.getServiceModules(Category.Network)) {
			try {
				String identifier = api.createNetwork(serviceModule.getConfig());
				ids.add(new Identifier(identifier, serviceModule.getCategory(), serviceModule.getSize()));
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Network: " + e.getMessage());
				e.printStackTrace();
				rollback(ids);
				return;
			}
		}
		
		for(ServiceModule serviceModule : service.getServiceModules(Category.Storage)) {
			try {
				String identifier = api.createStorage(serviceModule.getConfig());
				ids.add(new Identifier(identifier, serviceModule.getCategory(), serviceModule.getSize()));
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Storage: " + e.getMessage());
				e.printStackTrace();
				rollback(ids);
				return;
			}
		}
		
		for(ServiceModule serviceModule : service.getServiceModules(Category.Compute)) {
			try {
				String identifier = api.createCompute(serviceModule.getConfig());
				ids.add(new Identifier(identifier, serviceModule.getCategory(), serviceModule.getSize()));
			} catch (LibvirtException e) {
				LOGGER.error("Could not create Compute: " + e.getMessage());
				e.printStackTrace();
				rollback(ids);
				return;
			}
		}
		
		orderedServiceRepo.save(new OrderedService(service.getServiceName(),ids));
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
		
		orderedServiceRepo.delete(orderedService);
	}
	
	//Nur Testweise
	private void rollback(Set<Identifier> identifiers) {
		cancelService(new OrderedService("rollback", identifiers));
	}

}
