package blogappapi.blogappapi.repository;

import blogappapi.blogappapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
