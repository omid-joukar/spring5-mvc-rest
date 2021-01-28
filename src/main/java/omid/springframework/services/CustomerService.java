package omid.springframework.services;

import omid.springframework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    CustomerDTO saveCustomerByDTO(Long id , CustomerDTO customerDTO);
    CustomerDTO patchCustomer(Long id , CustomerDTO customerDTO);
    void deleteCustomreById(Long id);
}
