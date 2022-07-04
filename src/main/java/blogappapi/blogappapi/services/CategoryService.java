package blogappapi.blogappapi.services;

import blogappapi.blogappapi.entities.Category;
import blogappapi.blogappapi.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    // create
    public CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    //delete
    void deleteCatetory(Integer categoryId);
    // get
    CategoryDto getCategory(Integer categoryId);
    // get all
    List<CategoryDto> getAllCategory();
}

