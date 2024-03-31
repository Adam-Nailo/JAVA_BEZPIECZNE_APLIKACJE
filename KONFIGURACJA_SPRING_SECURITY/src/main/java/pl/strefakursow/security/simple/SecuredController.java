package pl.strefakursow.security.simple;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;

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

    @GetMapping("/secured-secret")
    public String secretPage() {
        return "secured-secret";
    }

    @GetMapping("/secured-basic/admin")
    public String adminPage() {
        return "admin";
    }

    @RolesAllowed({"EDITOR"})
    @GetMapping("/secured-basic/edit")
    public String editPage() {
        return "edit";
    }

    @PreAuthorize("hasRole('ADMIN') || #secretParam =='SECRET'")
    @GetMapping("/secured-form/expr")
    public String pageProtectedBySecurityExpression(@P("secretParam")
            @RequestParam(value = "secret", required = false) String secretParam) {
        return "expr";
    }
}
