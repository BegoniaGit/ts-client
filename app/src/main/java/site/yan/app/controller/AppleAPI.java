package site.yan.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppleAPI {

    @GetMapping("/get")
    public String getApple(){
        return "this a apple";
    }
}
