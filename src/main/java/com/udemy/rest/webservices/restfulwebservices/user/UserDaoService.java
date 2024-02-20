package com.udemy.rest.webservices.restfulwebservices.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userId=0;
    static {
        users.add(new User(++userId, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++userId, "Anil", LocalDate.now().minusYears(20)));
        users.add(new User(++userId, "Eve", LocalDate.now().minusYears(34)));
    }
    public List<User> findAll() {
        return users;
    }
//    public User findById(int id) {
//        for (User user : users) {
//            if (user.getId() == id) {
//                return user;
//            }
//        }
//        return null;
//    }


    public User findById(int id){
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
    public ResponseEntity<User> addNewUser(User user){
        user.setId(++userId);
        users.add(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }

//    public ResponseEntity<User> deleteUser(User user){
//          User userToRemove = findById(user.getId());
//          if(userToRemove!=null) {
//              users.remove(userToRemove);
//              return ResponseEntity.accepted().body(userToRemove);
//          }else {
//              return ResponseEntity.notFound().build();
//          }
//    }

    public User deleteUser(int id){
        Predicate<? super User> predicate = user-> user.getId()==id;
        users.removeIf(predicate);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }
}
