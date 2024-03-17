package pl.strefakursow.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecuredController {

    @GetMapping("/secured-basic")
    public String securedPage() {
        return "secured-basic";
    }

    @GetMapping("/secured-form")
    public String formSecuredPage() {
        return "secured-form";
    }

    @GetMapping("/secured-form/login")
    public String formLogin() {
        return "login";
    }
}
