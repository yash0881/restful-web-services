package com.udemy.rest.webservices.restfulwebservices.jpa;

import com.udemy.rest.webservices.restfulwebservices.user.Post;
import com.udemy.rest.webservices.restfulwebservices.user.PostRepository;
import com.udemy.rest.webservices.restfulwebservices.user.User;
import com.udemy.rest.webservices.restfulwebservices.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResourceController {


    private UserRepository userRepository;

    private PostRepository postRepository;

    @Autowired
    public UserJpaResourceController( UserRepository userRepository, PostRepository postRepository){
         this.userRepository =userRepository;
         this.postRepository=postRepository;
    }
    @GetMapping(path = "/jpa/users")
    public List<User> retreiveAllUser(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable int id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retreiveAllUser());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user ){
        User savedUser =  userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id").buildAndExpand(savedUser.getId()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUserById(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public  List<Post> retreivePostsForAUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);
        return user.get().getPost();
    }


    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPostsForAUser(@PathVariable int id, @Validated @RequestBody Post post ){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id:"+id);
        post.setUser(user.get());
        Post savedPost  = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
