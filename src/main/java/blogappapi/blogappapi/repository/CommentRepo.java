package blogappapi.blogappapi.repository;

import blogappapi.blogappapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
