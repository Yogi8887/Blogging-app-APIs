package blogappapi.blogappapi.services;

import blogappapi.blogappapi.entities.Post;
import blogappapi.blogappapi.payloads.PostDto;
import blogappapi.blogappapi.payloads.PostResponse;
import javafx.geometry.Pos;

import java.util.List;

public interface PostService {
    // create
    PostDto createPost(PostDto postDto ,Integer userId, Integer categoryId );
    // update
    PostDto updatePost(PostDto postDto, Integer postId);
    // delete
    void deletePost(Integer postId);
    // get
    PostDto getPostById(Integer postId);

    // get all post
    List<PostDto> getAllPost();

    // get all post by category Id
    List<PostDto> getPostsByCategoryId(Integer categoryId);

    // get all posts by user Id
    List<PostDto> getPostsByUserID(Integer userId);

    PostResponse getAllPostPaging(Integer pageNumber, Integer pageSize);

    PostResponse getAllPostPagingSort(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);

    List<PostDto> searchPosts(String keywords);
}
