package sddc.services;

import org.libvirt.LibvirtException;

import sddc.services.domain.Category;
import sddc.services.domain.OrderedService;
import sddc.services.domain.Service;
import sddc.services.domain.ServiceModule;
import sddc.services.genericapi.IGenericAPIFacade;

public class ServiceAccess {
	
	private IGenericAPIFacade api;
	
	public ServiceAccess (IGenericAPIFacade api) {
		this.api = api;
	}

	public void orderService(Service service) {
		for(ServiceModule serviceModule : service.getServiceModules(Category.Network)) {
			try {
				api.createCompute("");
			} catch (LibvirtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteService(OrderedService orderedService) {
		
	}
	
	
	
	
}
