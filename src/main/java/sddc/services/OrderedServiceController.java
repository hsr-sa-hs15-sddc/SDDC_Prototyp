package sddc.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sddc.services.domain.OrderedService;

@RestController
public class OrderedServiceController {
	
	@Autowired
	private OrderedServiceRepo repo;
	
	 @PostConstruct
	 private void createInitialData() {
		 repo.deleteAll();
		 List<String> lst = new ArrayList<String>();
		 lst.add("213123123123123");
		 lst.add("asddsadasdas");
		 repo.save(new OrderedService("Hello World",lst));
	 }
	
    @RequestMapping("/orderedservices")
    @ResponseBody
    public List<OrderedService> findAllServices() {
        List<OrderedService> result = repo.findAll();
        return result;
    }
}
