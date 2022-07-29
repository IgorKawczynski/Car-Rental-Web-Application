package pl.igokaw;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class WebController {

    @GetMapping("/")
    public String getListItems() {
        return "HOMEPAGE";
    }

}
