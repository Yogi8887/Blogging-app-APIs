package blogappapi.blogappapi.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotBlank
    @Size(min = 4,message = "Category name must be more than 4 character!!")
    private String categoryName;
    @NotBlank
    @Size(min = 10,message = "Category Description must be more than 10 character!!")
    private String categoryDescription;
}
