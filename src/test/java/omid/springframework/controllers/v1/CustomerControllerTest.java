package omid.springframework.controllers.v1;

import omid.springframework.api.v1.model.CustomerDTO;

import omid.springframework.controllers.RestResponseEntityExceptionHandler;
import omid.springframework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static omid.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {
    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController customerController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllCustomers() throws Exception {

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("omid");
        customerDTO1.setLastname("joukar");
        customerDTO1.setCustomerUrl("/api/v1/customer/1");
        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname("Ali");
        customerDTO2.setLastname("Masoomi");
        customerDTO1.setCustomerUrl("/api/v1/customer/2");
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerDTOS.add(customerDTO1);
        customerDTOS.add(customerDTO2);
        when(customerService.getAllCustomers()).thenReturn(customerDTOS);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerById() throws Exception {

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("omid");
        customerDTO1.setLastname("joukar");
        customerDTO1.setCustomerUrl("/api/v1/customer/1");
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("omid")));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("Fred");
        customer.setLastname("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname("omid");
        customer.setLastname("joukar");
        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstname(customer.getFirstname());
        returnDto.setLastname(customer.getLastname());
        returnDto.setCustomerUrl("/api/v1/customers/1");
        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDto);
        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("omid")))
                .andExpect(jsonPath("$.lastname", equalTo("joukar")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomreById(anyLong());
    }
}
