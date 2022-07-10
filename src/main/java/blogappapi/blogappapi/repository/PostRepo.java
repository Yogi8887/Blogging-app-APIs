package blogappapi.blogappapi.repository;

import blogappapi.blogappapi.entities.Category;
import blogappapi.blogappapi.entities.Post;
import blogappapi.blogappapi.entities.User;
import blogappapi.blogappapi.payloads.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    //searching
//    @Query("select p from Post p where p.title like : key")
//    List<Post> findByTitleContain(@Param("key") String title);

    List<Post> findByTitleContaining(String keywords);
}






