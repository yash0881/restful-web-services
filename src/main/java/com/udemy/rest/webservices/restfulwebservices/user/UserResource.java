package com.udemy.rest.webservices.restfulwebservices.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {
    private UserDaoService service;
    public UserResource(UserDaoService service){
        this.service=service;
    }
    @GetMapping(path = "/users")
    public List<User> retreiveAllUser(){
                    return service.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable int id) throws UserNotFoundException {
        User user = service.findById(id);
        if(user == null)
            throw new UserNotFoundException("id:"+id);
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retreiveAllUser());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user ){
        return service.addNewUser(user);
    }

    @DeleteMapping("/users/{id}")
    public User deleteUserById(@PathVariable int id){
        return service.deleteUser(id);
    }
}
