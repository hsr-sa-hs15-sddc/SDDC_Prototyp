package sddc.services;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sddc.services.domain.Category;
import sddc.services.domain.Identifier;
import sddc.services.domain.OrderedService;
import sddc.services.domain.Service;
import sddc.services.domain.Size;

@RestController
public class OrderedServiceController {
	
	@Autowired
	private OrderedServiceRepo repo;
	
	 @PostConstruct
	 private void createInitialData() {
		 repo.deleteAll();
		 Set<Identifier> ids = new HashSet<Identifier>();
		 ids.add(new Identifier("Hello World",Category.Compute,Size.L));
		 ids.add(new Identifier("Hello World",Category.Network,Size.S));
		 repo.save(new OrderedService("Hello World",ids));
	 }
	
    @RequestMapping("/orderedservices")
    @ResponseBody
    public List<OrderedService> findAllServices() {
        List<OrderedService> result = repo.findAll();
        return result;
    }
    
    @RequestMapping(value="/orderedservices/{id}",method = RequestMethod.GET)
    @ResponseBody
    public OrderedService findService(@PathVariable("id") long id){
        return repo.findOne(id);
    }
    
    @RequestMapping(value = "/orderedservices/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String orderService(@RequestBody OrderedService service){
     repo.delete(service.getId());
     return "ok";
    }
}
