package omid.springframework.repositories;

import omid.springframework.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
    Vendor findByName(String name);
}
