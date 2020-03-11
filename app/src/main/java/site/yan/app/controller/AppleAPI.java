package site.yan.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.yan.app.Repository.UserDao;
import site.yan.app.model.Apple;
import site.yan.app.model.UserDO;
import site.yan.app.service.Myservice;
import site.yan.httpclient.builder.HttpClientBuilder;
import site.yan.httpclient.builder.TargetLocationType;

@RestController
@RequestMapping("/")
public class AppleAPI {

    @Autowired
    UserDao userDao;

    @Autowired
    Myservice myservice;

    @PostMapping("/add")
    public Apple addApple(@RequestBody Apple apple) {
        return apple;
    }

    @GetMapping("/get")
    public String getApple(String color) {
        return color;
    }

    @PutMapping("/put")
    public String putApple(String color) {
        return color;
    }

    @DeleteMapping("/delete")
    public String deleteApple(String color) {
        return color;
    }


    @GetMapping("/inner/server")
    public String innerServer() {
        return "hello,succeed";
    }

    @PutMapping("/tri")
    public void taskTrigger() throws Exception {
//        if(true)throw new Exception("I am a exception");

//        myservice.getName("d",3);
//        HttpClientBuilder.builder().targetLocation(TargetLocationType.INTERNAL)
//                .build().doGet("http://127.0.0.1:90/inner/server");
//
//        HttpClientBuilder.builder().targetLocation(TargetLocationType.INTERNAL)
//                .build().doGet("http://127.0.0.1:90/inner/server");
//
//        HttpClientBuilder.builder().targetLocation(TargetLocationType.INTERNAL)
//                .build().doGet("http://127.0.0.1:90/inner/server");


        HttpClientBuilder.builder().targetLocation(TargetLocationType.EXTERNAL)
                .build().doGet("http://yanyan.site");


        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setName(myservice.getName("me", 2));
        userDO.setAccount("fengqy");
        userDO.setPwd("123456");
        userDao.save(userDO);
        userDao.findById(1L);
    }

}
