package omid.springframework.bootstrap;

import omid.springframework.domain.Category;
import omid.springframework.domain.Customer;
import omid.springframework.domain.Vendor;
import omid.springframework.repositories.CategoryRepository;
import omid.springframework.repositories.CustomerRepository;
import omid.springframework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class BootStrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public BootStrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadCustomers();

        loadategories();

        loadVendors();


    }

    private void loadVendors() {
        Vendor vendor = new Vendor();
        vendor.setName("ALL Fruits are Here");
        vendorRepository.save(vendor);

        Vendor vendor1 = new Vendor();
        vendor1.setName("All Vege are Here");
        vendorRepository.save(vendor1);
    }

    private void loadategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");
        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);


        System.out.println("Data loaded : " + categoryRepository.count());
    }

    private void loadCustomers(){
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");
        customerRepository.save(customer2);


        System.out.println("Customers added : "+customerRepository.count());
    }
}
