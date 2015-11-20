package sddc.services.domain;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.libvirt.LibvirtException;

import sddc.services.genericapi.GenericAPILibVirt;
import sddc.services.genericapi.IGenericAPIFacade;



public class WorkflowTest {

	private IGenericAPIFacade api;
	private String storageConfig, networkConfig, computeConfig;
	private ServiceModule module1, module2, module3;
	private Set<ServiceModule> modules = new HashSet();

	@Before
	public void setUp() throws Exception {
		api = new GenericAPILibVirt();
		storageConfig = "<pool type=\"disk\"><name>vdb2</name><source><device path='/dev/vdb2'/></source><target><path>/dev</path></target></pool>";
		networkConfig = "<network><name>default6</name><bridge name=\"virbr0\" /><forward mode=\"nat\"/><ip address=\"192.168.122.1\" netmask=\"255.255.255.0\">"
			+ "<dhcp><range start=\"192.168.122.2\" end=\"192.168.122.254\" /></dhcp>"
		    + "</ip><ip family=\"ipv6\" address=\"2001:db8:ca2:2::1\" prefix=\"64\" >"
		    +  "<dhcp><range start=\"2001:db8:ca2:2:1::10\" end=\"2001:db8:ca2:2:1::ff\" /></dhcp></ip></network>";
		computeConfig = "<domain type='test' xmlns:qemu='http://libvirt.org/schemas/domain/qemu/1.0'>"
		  + "<name>QEmu-fedora-i686</name><memory>219200</memory><os><type arch='i686' machine='pc'>hvm</type></os>"
		  + "<devices><emulator>/usr/bin/qemu-system-x86_64</emulator></devices><qemu:commandline>"
		  + "<qemu:arg value='-newarg'/><qemu:env name='QEMU_ENV' value='VAL'/></qemu:commandline></domain>";
		//module1 = new ServiceModule("Compute",Size.S,Category.Compute,computeConfig);
		//module2 = new ServiceModule("Storage",Size.M,Category.Storage,storageConfig);
		module3 = new ServiceModule("Network",Category.Network,networkConfig);
		//modules.add(module1);
		//modules.add(module2);
		modules.add(module3);

	}

	@After
	public void tearDown() throws Exception {
		api.disconnect();
	}
	
	/* Order Service */
	
	@Test
	public void testOrder() {
		Service service = new Service("Testservice",modules);
		Workflow workflow = new Workflow(api);
		workflow.orderService(service);
		System.out.println(service.getServiceModules());
	}
	
	
	
}
