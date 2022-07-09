package blogappapi.blogappapi.repository;

import blogappapi.blogappapi.entities.Category;
import blogappapi.blogappapi.entities.Post;
import blogappapi.blogappapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}






