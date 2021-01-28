package omid.springframework.controllers.v1;

import omid.springframework.api.v1.model.CategoryDTO;
import omid.springframework.services.CategoryService;
import omid.springframework.services.ResourceNotFoundException;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {
    private static final String NAME = "jim";
    @Mock
    CategoryService categoryService;
    @InjectMocks
    CategoryController categoryController;
    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }
    @Test
    public void testListCategories()throws Exception{
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(1L);
        categoryDTO1.setName(NAME);
        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName("Bob");
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        categoryDTOS.add(categoryDTO1);
        categoryDTOS.add(categoryDTO2);
        when(categoryService.getAllCategories()).thenReturn(categoryDTOS);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }
    @Test
    public void testGetByNameCategories()throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName(NAME);
        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/jim")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(NAME)));
    }
    @Test
    public void testGetByNameNotFound()throws Exception{
        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL+"/Foo")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}