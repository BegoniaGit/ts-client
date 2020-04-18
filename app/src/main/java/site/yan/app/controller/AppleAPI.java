package site.yan.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.yan.app.Repository.UserDao;
import site.yan.app.model.Apple;
import site.yan.app.model.UserDO;
import site.yan.app.service.Myservice;
import site.yan.httpclient.builder.HttpClientBuilder;
import site.yan.httpclient.builder.TargetLocationType;
import site.yan.httpclient.client.TSHttpClientOUT;
import site.yan.local.advice.RowTrace;

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

        try {
            HttpClientBuilder.builder().targetLocation(TargetLocationType.EXTERNAL)
                    .build().doGet("http://baidu.com");
        } catch (Exception e) {
        }

        myservice.getName("2", 2);

        UserDO userDO = new UserDO();
        userDO.setName("ii");
        userDO.setAccount("fengqy");
        userDO.setPwd("123456");
        userDao.save(userDO);
        userDao.findAll();
        return "hello,succeed";
    }

    @PutMapping("/tri")
    public void taskTrigger() throws Exception {

        new TSHttpClientOUT().setTargetLocationType(TargetLocationType.INTERNAL)
                .doGet("http://127.0.0.1:8080/inner/server");

        new TSHttpClientOUT().setTargetLocationType(TargetLocationType.EXTERNAL)
                .doGet("http://baidu.com");


        myservice.getName("2", 2);

        UserDO userDO = new UserDO();
        userDO.setName("ii");
        userDO.setAccount("fengqy");
        userDO.setPwd("123456");
        userDao.save(userDO);
        userDao.findAll();
        RowTrace.start("一行代码", "ha ");
        System.out.println("dd");
        RowTrace.end();
    }

}
