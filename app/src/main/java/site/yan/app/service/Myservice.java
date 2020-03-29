package site.yan.app.service;

import org.springframework.stereotype.Service;
import site.yan.local.annotation.Traced;

import java.util.Random;

@Service
public class Myservice {

    @Traced
    public String getName(String s, int n) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return s + "apple";
    }
}
