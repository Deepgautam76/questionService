package com.deep.question_service.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class HomeController {
    @RequestMapping("/")
    public String greet(){
        return "Hello welcome to the question Service home controller";
    }
}
