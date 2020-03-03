package site.yan.app.controller;

import org.springframework.web.bind.annotation.*;
import site.yan.httpclient.HttpClientUtil;
import site.yan.app.model.Apple;

@RestController
@RequestMapping("/")
public class AppleAPI {

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
        while (true) {
            String resp = HttpClientUtil.getInstance().sendHttpGet("http://127.0.0.1:90/inner/server");
            System.out.println(resp);
            Thread.sleep(5 * 1000);
        }
    }
}
