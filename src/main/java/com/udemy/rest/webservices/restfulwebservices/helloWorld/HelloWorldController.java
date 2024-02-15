package com.udemy.rest.webservices.restfulwebservices.helloWorld;

import org.springframework.web.bind.annotation.*;

// Rest API
@RestController
public class HelloWorldController {

    // hello-world


//    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
//    public String hello(){
//        return "Hello World";
//    }


    @GetMapping(path = "/hello-world")
    public String hello() {
        String s = "Hello World";
        return s;
    }


    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }


    @GetMapping(path = "/hello-world/path-variable/{name}")
    public String helloWorldBean(@PathVariable String name) {
        return ("Hello World " + name);
    }

}
