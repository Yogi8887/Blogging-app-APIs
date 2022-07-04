package blogappapi.blogappapi.controllers;

import blogappapi.blogappapi.payloads.ApiResponse;
import blogappapi.blogappapi.payloads.CategoryDto;
import blogappapi.blogappapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto catDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(catDto, HttpStatus.CREATED);
    }
    // update
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        CategoryDto catDto = this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<CategoryDto>(catDto,HttpStatus.OK);
    }
    // delete
    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCatetory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully!",true),HttpStatus.OK);
    }
    // get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
        CategoryDto categoryDto= this.categoryService.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
    }
    // get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDtoList = this.categoryService.getAllCategory();
        return new ResponseEntity<List<CategoryDto>>(categoryDtoList,HttpStatus.OK);
    }
}
