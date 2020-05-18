package site.yan.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.yan.app.repository.UserDao;
import site.yan.app.model.*;
import site.yan.app.service.Myservice;
import site.yan.core.utils.TimeUtil;
import site.yan.httpclient.builder.HttpClientBuilder;
import site.yan.httpclient.builder.TargetLocationType;
import site.yan.httpclient.client.TsHttpClient;
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

        new TsHttpClient().setTargetLocationType(TargetLocationType.EXTERNAL)
                .doGet("http://163.com");

        myservice.getName("苏碧");

        UserDO userDO = new UserDO();
        userDO.setName("苏碧");
        userDO.setAccount("565435688");
        userDO.setPwd("123456");
        userDao.save(userDO);
        userDao.findAll();
        return "hello,succeed";
    }

    @PutMapping("/tri")
    public void taskTrigger() throws Exception {

        new TsHttpClient().setTargetLocationType(TargetLocationType.INTERNAL)
                .doGet("http://114.116.251.70:8080/app/inner/server");

        new TsHttpClient().setTargetLocationType(TargetLocationType.EXTERNAL)
                .doGet("http://163.com");


        myservice.getName("柳岸溪");

        UserDO userDO = new UserDO();
        userDO.setName("柳岸溪");
        userDO.setAccount("31415926");
        userDO.setPwd("123456");
        userDao.save(userDO);
        userDao.findAll();

        RowTrace.start("一个行级代码拦截", "我是一个耗时的代码段");
        TimeUtil.sleep(200);
        RowTrace.end();
    }

}
