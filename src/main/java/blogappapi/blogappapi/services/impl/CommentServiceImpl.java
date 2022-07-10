package blogappapi.blogappapi.services.impl;

import blogappapi.blogappapi.entities.Comment;
import blogappapi.blogappapi.entities.Post;
import blogappapi.blogappapi.exceptions.ResourceNotFoundException;
import blogappapi.blogappapi.payloads.CommentDto;
import blogappapi.blogappapi.repository.CommentRepo;
import blogappapi.blogappapi.repository.PostRepo;
import blogappapi.blogappapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto,Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "comment id", commentId));
        this.commentRepo.delete(comment);
    }
}
