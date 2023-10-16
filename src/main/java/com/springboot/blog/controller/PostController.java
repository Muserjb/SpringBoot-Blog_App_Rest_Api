package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostDto2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    // get all posts rest api
    @GetMapping("/api/v1/posts")
    // @PreAuthorize("hasRole('USER')")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }
    // create blog post rest api
    @PostMapping("/api/v1/posts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }



    // get post by id
    // Testing versioning Urls
    // @GetMapping(value = "api/posts/{id}",params = "version=1")
   // @GetMapping(value = "/api/posts/{id}",headers = "X-API-VERSION=1")
   // @GetMapping(value = "api/posts/{id}", produces = "application/vnd.javaguides.v1+json")
    @GetMapping(value = "api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostByIdv1(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
/*
 was created just for practicing visioning rest API`s

//    @GetMapping(value = "api/v2/posts/{id}")
//    @GetMapping(value = "api/posts/{id}",params = "version=2")
    //@GetMapping(value = "api/posts/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "api/posts/{id}", produces = "application/vnd.javaguides.v2+json")
    public ResponseEntity<PostDto2> getPostByIdv2(@PathVariable(name = "id") long id){
        PostDto postDto = postService.getPostById(id);
        PostDto2 postDto2 = new PostDto2();
        //converting postDto tp postDto2
        postDto2.setId(postDto.getId());
        postDto2.setTitle(postDto.getTitle());
        postDto2.setContent(postDto.getContent());
        postDto2.setDescription(postDto.getDescription());
        List<String> tags = new ArrayList<>();

        tags.add("Java");
        tags.add("Php");
        tags.add("AWS");
        postDto2.setTags(tags);

        return ResponseEntity.ok(postDto2);
    }
 */

    // update post by id rest api
    @PutMapping("api/v1/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){

       PostDto postResponse = postService.updatePost(postDto, id);

       return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // delete post rest api
    @DeleteMapping("api/v1/posts/{id}")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}
