package omid.springframework.services;

import omid.springframework.api.v1.mapper.CustomerMapper;
import omid.springframework.bootstrap.BootStrap;
import omid.springframework.domain.Customer;
import omid.springframework.model.CustomerDTO;
import omid.springframework.repositories.CategoryRepository;
import omid.springframework.repositories.CustomerRepository;
import omid.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;
    @Before
    public void setUp()throws Exception{
        System.out.println("Loading Data");
        System.out.println(customerRepository.findAll().size());
        BootStrap bootStrap = new BootStrap(categoryRepository,customerRepository, vendorRepository);
        bootStrap.run();
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE,customerRepository);
    }
    @Test
    public void patchCustomerUpdateFirstName()throws Exception{
        String updateName = "UpdateName";
        long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updateName);
        customerService.patchCustomer(id,customerDTO);
        Customer updatedCustomer = customerRepository.findById(id).get();
        assertNotNull(updatedCustomer);
        assertEquals(updateName,updatedCustomer.getFirstname());
        assertThat(originalFirstName ,not(equalTo(updatedCustomer.getFirstname())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastname()));
    }
    @Test
    public void patchCustomerUpdateLastName()throws Exception{
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first/last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastname());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstname()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastname())));
    }
    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();
        System.out.println("CustoMERS Found : "+customers.size());
        return customers.get(0).getId();
    }
}
