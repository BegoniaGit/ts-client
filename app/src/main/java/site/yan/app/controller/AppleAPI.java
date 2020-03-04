package site.yan.app.controller;

import org.springframework.web.bind.annotation.*;
import site.yan.app.model.Apple;
import site.yan.httpclient.client.TSHttpClientIN;
import site.yan.httpclient.client.TSHttpClientOUT;
import site.yan.httpclient.trace.TSHttpClient;

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
            String resp = new TSHttpClientIN().doGet("http://127.0.0.1:90/inner/server");
            String resp2=new TSHttpClientOUT().doGet("http://www.baidu.com");
            System.out.println(resp);
    }
}
