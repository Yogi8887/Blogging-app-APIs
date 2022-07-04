package blogappapi.blogappapi.services.impl;

import blogappapi.blogappapi.entities.Category;
import blogappapi.blogappapi.exceptions.ResourceNotFoundException;
import blogappapi.blogappapi.payloads.CategoryDto;
import blogappapi.blogappapi.repository.CategoryRepo;
import blogappapi.blogappapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category category = this.categoryRepo.save(cat);
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("category","CategoryId",categoryId));
        cat.setCategoryName(categoryDto.getCategoryName());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat,CategoryDto.class);
    }

    @Override
    public void deleteCatetory(Integer categoryId) {
        Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException(
           "Category","CategoryId",categoryId));
        this.categoryRepo.delete(cat);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException(
                "Category","CategoryId",categoryId));
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto>  catDto = categories.stream()
                .map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return catDto;
    }
}
