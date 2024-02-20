package jpa;

import com.udemy.rest.webservices.restfulwebservices.user.User;
import com.udemy.rest.webservices.restfulwebservices.user.UserDaoService;
import com.udemy.rest.webservices.restfulwebservices.user.UserNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {
    private UserDaoService service;

    private UserRepository repository;
    public UserJpaResource(UserDaoService service, UserRepository repository){
        this.service=service; this.repository=repository;
    }
    @GetMapping(path = "/jpa/users")
    public List<User> retreiveAllUser(){
                    return repository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable int id) throws UserNotFoundException {
        User user = service.findById(id);
        if(user == null)
            throw new UserNotFoundException("id:"+id);
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retreiveAllUser());
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user ){
        return service.addNewUser(user);
    }

    @DeleteMapping("/jpa/users/{id}")
    public User deleteUserById(@PathVariable int id){
        return service.deleteUser(id);
    }
}
