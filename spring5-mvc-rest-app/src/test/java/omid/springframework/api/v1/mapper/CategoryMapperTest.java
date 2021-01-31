package omid.springframework.api.v1.mapper;

import omid.springframework.api.v1.model.CategoryDTO;
import omid.springframework.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {
    CategoryMapper categoryMapper  = CategoryMapper.INSTANCE;
    @Test
    void categoryToCategoryDTO() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Joe");
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        assertEquals(Long.valueOf(1L),categoryDTO.getId());
        assertEquals(category.getName(),categoryDTO.getName());
    }
}