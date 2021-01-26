package omid.springframework.services;

import omid.springframework.api.v1.mapper.CategoryMapper;
import omid.springframework.api.v1.model.CategoryDTO;
import omid.springframework.domain.Category;
import omid.springframework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {
    @Mock
    CategoryRepository categoryRepository;
    CategoryService categoryService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    void getAllCategories() {
        List<Category> categories = Arrays.asList(new Category() , new Category() , new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        assertEquals(3,categoryDTOS.size());
    }

    @Test
    void getCategoryByName() {
    Category category = new Category();
    category.setName("ff");
    category.setId(1L);
    when(categoryRepository.findByName(anyString())).thenReturn(category);
    CategoryDTO categoryDTO = categoryService.getCategoryByName("ff");
    assertEquals(categoryDTO.getId(),category.getId());
    assertEquals(categoryDTO.getName(),category.getName());

    }
}