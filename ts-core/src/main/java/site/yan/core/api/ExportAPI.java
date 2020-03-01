package site.yan.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ExportAPI {

    @GetMapping("/trace")
    public String export() {
        return "this a api for export data about trace record. ";
    }
}
