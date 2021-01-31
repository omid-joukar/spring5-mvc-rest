package omid.springframework.repositories;

import omid.springframework.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
}
