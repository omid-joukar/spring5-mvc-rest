package omid.springframework.services;

import omid.springframework.api.v1.mapper.VendorMapper;
import omid.springframework.api.v1.model.VendorDTO;
import omid.springframework.domain.Vendor;
import omid.springframework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl("/api/v1/vendors/"+vendor.getId());
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl("/api/v1/vendors/"+id);
                    return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedVendorDTO.setVendorUrl("/api/v1/vendor/"+savedVendor.getId());
        return vendorDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor  = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnedVendorDTO.setVendorUrl("/api/v1/vendor/"+savedVendor.getId());
        return vendorDTO;

    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                                .map(vendor -> {
                                    if (vendorDTO.getName() != null){
                                        vendor.setName(vendorDTO.getName());
                                    }
                                    VendorDTO returnedDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                                    returnedDTO.setVendorUrl("/api/v1/vendors/"+vendor.getId());
                                    return vendorDTO;
                                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }
}
