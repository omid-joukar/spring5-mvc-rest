package omid.springframework.services;

import omid.springframework.api.v1.mapper.CustomerMapper;
import omid.springframework.api.v1.model.CustomerDTO;
import omid.springframework.domain.Customer;
import omid.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                       CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                       customerDTO.setCustomerUrl("/api/v1/customer/"+customer.getId());
                       return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        returnedCustomerDTO.setCustomerUrl("/api/v1/customer/"+savedCustomer.getId());
        return returnedCustomerDTO;
    }
}
