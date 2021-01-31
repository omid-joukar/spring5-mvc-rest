package omid.springframework.services;

import java.util.List;
import omid.springframework.model.CustomerDTO;
public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    CustomerDTO saveCustomerByDTO(Long id , CustomerDTO customerDTO);
    CustomerDTO patchCustomer(Long id , CustomerDTO customerDTO);
    void deleteCustomreById(Long id);
}
