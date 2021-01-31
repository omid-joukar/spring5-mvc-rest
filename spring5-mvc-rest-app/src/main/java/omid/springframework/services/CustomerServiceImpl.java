package omid.springframework.services;

import lombok.extern.slf4j.Slf4j;
import omid.springframework.api.v1.mapper.CustomerMapper;
import omid.springframework.api.v1.model.CustomerDTO;
import omid.springframework.controllers.v1.CustomerController;
import omid.springframework.domain.Customer;
import omid.springframework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
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
                       customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                       return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl(getCustomerUrl(id));
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));

    }
    private CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        returnedCustomerDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
        return returnedCustomerDTO;
    }
    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if (customerDTO.getFirstname() != null){
                        customer.setFirstname(customerDTO.getFirstname());
                    }
                    if (customerDTO.getLastname() != null){
                        customer.setLastname(customerDTO.getLastname());
                    }
                    CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
                    returnDTO.setCustomerUrl(getCustomerUrl(id));
                    return returnDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }
    private String getCustomerUrl(Long id){
        return CustomerController.BASE_URL+ "/"+id;
    }
    @Override
    public void deleteCustomreById(Long id) {
        customerRepository.deleteById(id);
    }
}
