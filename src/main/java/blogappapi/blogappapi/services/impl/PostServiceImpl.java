package blogappapi.blogappapi.services.impl;

import blogappapi.blogappapi.entities.Category;
import blogappapi.blogappapi.entities.Post;
import blogappapi.blogappapi.entities.User;
import blogappapi.blogappapi.exceptions.ResourceNotFoundException;
import blogappapi.blogappapi.payloads.PostDto;
import blogappapi.blogappapi.payloads.PostResponse;
import blogappapi.blogappapi.repository.CategoryRepo;
import blogappapi.blogappapi.repository.PostRepo;
import blogappapi.blogappapi.repository.UserRepo;
import blogappapi.blogappapi.services.PostService;
import javafx.geometry.Pos;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","user Id",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category Id",categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post save = this.postRepo.save(post);
        return this.modelMapper.map(save,PostDto.class);

    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","Post id",postId));
        this.postRepo.delete(post);

    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post posts = this.postRepo.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post","Post Id",postId));
        return this.modelMapper.map(posts,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = this.postRepo.findAll();
        List<PostDto> postList = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postList;
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("category","category Id",categoryId));
        List<Post> posts= this.postRepo.findByCategory(category);
        List<PostDto> postList = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postList;
    }

    @Override
    public List<PostDto> getPostsByUserID(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User","User id ", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postLists = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postLists;
    }

    // pagenation and sorting
    @Override
    public PostResponse getAllPostPaging(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = this.postRepo.findAll(page);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }
// pageable and sorting
    @Override
    public PostResponse getAllPostPagingSort(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
      //  Pageable page = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
       // Pageable page = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());
//        if(sortDir.equalsIgnoreCase("asc")){
//            sort = sort.by(sortBy).ascending();
//        }else {
//            sort = sort.by(sortBy).descending();
//        }
        Sort sort =null;
        sort = (sortDir.equalsIgnoreCase("asc")) ? sort.by(sortBy).ascending() : sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber,pageSize,sort);

        Page<Post> pagePost = this.postRepo.findAll(page);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> searchPosts(String keywords) {
      // List<Post> posts =  this.postRepo.findByTitleContain("%"+keywords+"%");
       List<Post> posts =  this.postRepo.findByTitleContaining(keywords);
        List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

}
