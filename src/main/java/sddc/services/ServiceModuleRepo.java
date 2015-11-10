package sddc.services;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import sddc.services.domain.ServiceModule;

public interface ServiceModuleRepo extends JpaRepository<ServiceModule, Long> {
	Set<ServiceModule> findByName(String name);
}
