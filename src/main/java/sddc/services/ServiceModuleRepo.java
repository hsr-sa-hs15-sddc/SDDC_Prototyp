package sddc.services;


import org.springframework.data.jpa.repository.JpaRepository;

import sddc.services.domain.Service;
import sddc.services.domain.ServiceModule;

public interface ServiceModuleRepo extends JpaRepository<ServiceModule, Long> {
}
