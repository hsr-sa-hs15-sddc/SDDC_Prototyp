package sddc.services.domain;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sddc.ApplicationMain;
import sddc.services.OrderedServiceRepo;
import sddc.services.ServiceModuleRepo;
import sddc.services.ServiceRepo;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationMain.class,
        initializers = ConfigFileApplicationContextInitializer.class)
@Configuration
@Profile("dev")
public class WorkflowTest {

	private String storageConfig, networkConfig, computeConfig;
	private ServiceModule module1, module2, module3;
	private Set<ServiceModule> modules = new HashSet<ServiceModule>();
	private Service  service;
	
	@Autowired
	private OrderedServiceRepo orderedRepo;
	
	@Autowired
	private ServiceModuleRepo modulesRepo;
	
	@Autowired
	private ServiceRepo repo;
	
	@Autowired
	private Workflow workflow;

	@Before
	public void setUp() throws Exception {
		storageConfig = "<pool type=\"disk\"><name>vdb2</name><source><device path='/dev/vdb2'/></source><target><path>/dev</path></target></pool>";
		networkConfig = "<network><name>default6</name><bridge name=\"virbr0\" /><forward mode=\"nat\"/><ip address=\"192.168.122.1\" netmask=\"255.255.255.0\">"
			+ "<dhcp><range start=\"192.168.122.2\" end=\"192.168.122.254\" /></dhcp>"
		    + "</ip><ip family=\"ipv6\" address=\"2001:db8:ca2:2::1\" prefix=\"64\" >"
		    +  "<dhcp><range start=\"2001:db8:ca2:2:1::10\" end=\"2001:db8:ca2:2:1::ff\" /></dhcp></ip></network>";
		computeConfig = "<domain type='test' xmlns:qemu='http://libvirt.org/schemas/domain/qemu/1.0'>"
		  + "<name>QEmu-fedora-i686</name><memory>219200</memory><os><type arch='i686' machine='pc'>hvm</type></os>"
		  + "<devices><emulator>/usr/bin/qemu-system-x86_64</emulator></devices><qemu:commandline>"
		  + "<qemu:arg value='-newarg'/><qemu:env name='QEMU_ENV' value='VAL'/></qemu:commandline></domain>";
		module1 = new ServiceModule("Compute",Size.S,Category.Compute,computeConfig);
		module2 = new ServiceModule("Storage",Size.M,Category.Storage,storageConfig);
		module3 = new ServiceModule("Network",Category.Network,networkConfig);
		modules.add(module1);
		modules.add(module2);
		modules.add(module3);
		service = new Service("Testservice",modules);
		repo.save(service);
		modulesRepo.save(modules);
	}
	
	@After
	public void tearDown() {
		orderedRepo.deleteAll();
		repo.deleteAll();
		orderedRepo.deleteAll();
	}
	
	/* Test Service */
	@Test
	public void testService() {
		Assert.assertEquals("Testservice",repo.findByServiceName("Testservice").getServiceName());
		Assert.assertTrue(repo.findByServiceName("Testservice").getServiceModules().size() == 3);
	}
	
	/* Test Modules */
	@Test
	public void testModules() {
		Assert.assertEquals("Network",modulesRepo.findByName("Network").getName());
		Assert.assertEquals( "Storage", modulesRepo.findByName("Storage").getName());
		Assert.assertEquals("Compute", modulesRepo.findByName("Compute").getName());
		Assert.assertTrue(modulesRepo.findAll().size() == 3);
	}
	
	/* Order Service */
	
	@Test
	public void testOrderAndCancelService() {
		workflow.orderService(service);
		Assert.assertEquals("Testservice",orderedRepo.findByName("Testservice").getOrderedServiceName());
		Assert.assertTrue(orderedRepo.count() > 0);
		Assert.assertTrue(orderedRepo.findByName("Testservice").getIdentifiers().size() == 3);
		workflow.cancelService(orderedRepo.findByName("Testservice"));
		Assert.assertNull(orderedRepo.findByName("Testservice"));
	}
	
	
}
