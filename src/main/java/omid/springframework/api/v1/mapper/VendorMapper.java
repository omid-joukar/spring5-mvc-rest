package omid.springframework.api.v1.mapper;

import omid.springframework.api.v1.model.VendorDTO;
import omid.springframework.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);
    Vendor vendorDTOToVendor(VendorDTO vendorDTO);
    VendorDTO vendorToVendorDTO(Vendor vendor);
}
