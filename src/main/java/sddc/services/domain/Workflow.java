package sddc.services.domain;

import org.libvirt.LibvirtException;

import sddc.services.genericapi.IGenericAPIFacade;

public class Workflow {
	
	private IGenericAPIFacade api;
	
	//TODO: DI and Factory
	public Workflow(IGenericAPIFacade api) {
		this.api = api;
		try {
			api.connect("test:///defaul", false);
		} catch (LibvirtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Refactoring + Logging
	public void orderService(Service service) {
		for(ServiceModule serviceModule : service.getServiceModules(Category.Network)) {
			try {
				api.createNetwork(null); //ServiceModule benötigt noch konfiguration
			} catch (LibvirtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(ServiceModule serviceModule : service.getServiceModules(Category.Storage)) {
			try {
				api.createStorage(null); //ServiceModule benötigt noch konfiguration
			} catch (LibvirtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		for(ServiceModule serviceModule : service.getServiceModules(Category.Compute)) {
			try {
				api.createCompute(null); //ServiceModule benötigt noch konfiguration
			} catch (LibvirtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//Refactoring + Logging
	public void cancelService(OrderedService orderedService) {
		for(Identifier identifier : orderedService.getIdentifiers(Category.Compute)) {
			try {
				api.deleteCompute(identifier.getUuid());
			} catch (LibvirtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(Identifier identifier : orderedService.getIdentifiers(Category.Storage)) {
			try {
				api.deleteStorage(identifier.getUuid());
			} catch (LibvirtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(Identifier identifier : orderedService.getIdentifiers(Category.Network)) {
			try {
				api.deleteNetwork(identifier.getUuid());
			} catch (LibvirtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
