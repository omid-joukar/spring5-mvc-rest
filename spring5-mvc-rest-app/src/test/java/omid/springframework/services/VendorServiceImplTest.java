package omid.springframework.services;

import omid.springframework.api.v1.mapper.VendorMapper;
import omid.springframework.api.v1.model.VendorDTO;
import omid.springframework.api.v1.model.VendorListDTO;
import omid.springframework.domain.Vendor;
import omid.springframework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {
    @Mock
    VendorRepository vendorRepository;
    VendorServiceImpl vendorService;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE,vendorRepository);
    }

    @Test
    public void getAllVendors() {

        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("omid");


        Vendor vendor2 = new Vendor();
        vendor2.setId(1L);
        vendor2.setName("ali");
        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1,vendor2));
        VendorListDTO vendorListDTO = vendorService.getAllVendors();
        assertEquals(2,vendorListDTO.getVendors().size());

    }

    @Test
    public void getVendorById() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("omid");
        given(vendorRepository.findById(anyLong())).willReturn(java.util.Optional.of(vendor));
        VendorDTO vendorDTO = vendorService.getVendorById(1L);
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(vendorDTO.getName(), is(equalTo(vendor.getName())));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByIdNotFound()throws Exception{
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());
        VendorDTO vendorDTO = vendorService.getVendorById(1L);
        then(vendorRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void createNewVendor() {
        Vendor savedVendor = new Vendor();
        savedVendor.setId(1L);
        savedVendor.setName("omid");
        when(vendorRepository.save(any())).thenReturn(savedVendor);
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(savedVendor.getName());
        vendorDTO.setVendorUrl("/api/v1/vendors/1");

        VendorDTO savedVendorDTO = vendorService.createNewVendor(vendorDTO);
        assertNotNull(savedVendorDTO);
        assertEquals(vendorDTO.getName(),savedVendorDTO.getName());
        assertEquals("/api/v1/vendors/1" , savedVendorDTO.getVendorUrl());

    }

    @Test
    public void saveVendorByDTO() {
        Vendor savedVendor = new Vendor();
        savedVendor.setId(1L);
        savedVendor.setName("omid");
        when(vendorRepository.save(any())).thenReturn(savedVendor);
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(savedVendor.getName());
        vendorDTO.setVendorUrl("/api/v1/vendors/1");

        VendorDTO savedVendorDTO = vendorService.saveVendorByDTO(1L,vendorDTO);
        assertNotNull(savedVendorDTO);
        assertEquals(vendorDTO.getName(),savedVendorDTO.getName());
        assertEquals("/api/v1/vendors/1" , savedVendorDTO.getVendorUrl());


    }

    @Test
    public void deleteVendorById() {
    Long id = 1L;
    vendorRepository.deleteById(id);
    verify(vendorRepository,times(1)).deleteById(anyLong());

    }
}