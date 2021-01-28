package omid.springframework.services;

import omid.springframework.api.v1.mapper.VendorMapper;
import omid.springframework.api.v1.model.VendorDTO;
import omid.springframework.bootstrap.BootStrap;
import omid.springframework.repositories.CategoryRepository;
import omid.springframework.repositories.CustomerRepository;
import omid.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceImplIT {


    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;

    VendorServiceImpl vendorService;

    @Before
    public void setUp() throws Exception {
        BootStrap bootStrap = new BootStrap(categoryRepository,customerRepository,vendorRepository);
        bootStrap.run();
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE,vendorRepository);
    }
    @Test
    public void testPatchwithName(){

        String updatedName = "omid";

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        vendorService.patchVendor(1L,vendorDTO);
        VendorDTO updatedVendor = vendorService.getVendorById(1L);

        assertNotNull(updatedVendor);
        assertEquals("/api/v1/vendors/1",updatedVendor.getVendorUrl());
        assertEquals(updatedName,updatedVendor.getName());

    }
}
