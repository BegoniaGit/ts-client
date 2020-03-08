package site.yan.app.service;

import org.springframework.stereotype.Service;
import site.yan.local.annotation.Traced;

@Service
public class Myservice {

    @Traced
    public String getName(String s, int n) {
        return s + "apple";
    }
}
