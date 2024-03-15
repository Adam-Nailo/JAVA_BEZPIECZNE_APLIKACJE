package pl.strefakursow.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secured")
public class SecuredController {

    @GetMapping
    public String securedPage() {
        return "secured";
    }
}
