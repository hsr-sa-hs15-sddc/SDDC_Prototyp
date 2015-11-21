package sddc.services;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import sddc.services.domain.Service;

public interface ServiceRepo extends JpaRepository<Service, Long> {
	Service findByServiceName(String serviceName);
}
