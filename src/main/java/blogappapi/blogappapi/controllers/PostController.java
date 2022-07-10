package blogappapi.blogappapi.controllers;
import blogappapi.blogappapi.cofig.AppConstants;
import blogappapi.blogappapi.payloads.ApiResponse;
import blogappapi.blogappapi.payloads.PostDto;
import blogappapi.blogappapi.payloads.PostResponse;
import blogappapi.blogappapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId)
    {
        PostDto createPost = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getByCategoryId(@PathVariable Integer categoryId){
        List<PostDto> postDtoList = this.postService.getPostsByCategoryId(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getByUserId(@PathVariable Integer userId){
        List<PostDto> postsList = this.postService.getPostsByUserID(userId);
        return new ResponseEntity<List<PostDto>>(postsList,HttpStatus.OK);
    }

    @GetMapping("post/{postId}")
    public ResponseEntity<PostDto> getByPostId(@PathVariable Integer postId){
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> allPost = this.postService.getAllPost();
        return new ResponseEntity<List<PostDto>>(allPost,HttpStatus.OK);
    }

    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>
                (new ApiResponse("Post deleted successfully!",true),HttpStatus.OK);
    }

    @PutMapping("post/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){

        PostDto postDtoUpdate = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(postDtoUpdate, HttpStatus.OK);
    }
    // pageaable
    @GetMapping("/posts/paging")
    public ResponseEntity<PostResponse> getAllPostsPaging(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize)
    {
        PostResponse allPost = this.postService.getAllPostPaging(pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
    }

    // pagenation and sorting
    @GetMapping("/posts/paging/sort")
    public ResponseEntity<PostResponse> getAllPostsSort(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
            )
    {
        PostResponse allPost = this.postService.getAllPostPagingSort(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
    }
    // searching
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
       List<PostDto> result = this.postService.searchPosts(keywords);
       return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }
}
