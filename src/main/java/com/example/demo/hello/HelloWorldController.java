package com.example.demo.hello;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(method = RequestMethod.GET, path = "/helloworld")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("/helloworld2")
    public String helloWorld2(){
        return "Hello World 2";
    }
    @GetMapping("/helloworld-bean")
    public UserDetails helloWorldBean(){
        return new UserDetails("Anonymous", "Tester", "Lagos");
    }
}
