package com.udemy.rest.webservices.restfulwebservices.helloWorld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

// Rest API
@RestController
public class HelloWorldController {

    // hello-world

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource){
        this.messageSource=messageSource;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }
//    @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
//    public String hello(){
//        return "Hello World";
//    }


    @GetMapping(path = "/hello-world")
    public String hello() {
        String s = "Hello World";
        return s;
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloInternational() {

        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
//        return "Hello World V2";
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
