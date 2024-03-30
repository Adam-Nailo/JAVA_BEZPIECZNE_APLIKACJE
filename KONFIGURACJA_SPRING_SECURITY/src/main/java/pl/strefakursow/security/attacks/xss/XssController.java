package pl.strefakursow.security.attacks.xss;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;

@Controller
public class XssController {

    @GetMapping("/xss/safe")
    public String xssSafePage() {
        return "xss-safe";
    }

    @PostMapping("/xss/safe")
    public ModelAndView handleXssSafePost(@RequestParam("name") String name) {
        return new ModelAndView("xss-safe", Map.of("name", name));
    }

    @GetMapping("/xss/vulnerable")
    public void xssVulerablePage(HttpServletResponse response) throws IOException {
        response.getWriter().write(xssVulnerable());
    }

    @PostMapping("/xss/vulnerable")
    public void handleXssVulneralbePost(HttpServletResponse response, @RequestParam("name") String name) throws IOException {

        response.getWriter().write(xssVulnerable().replace("${name}", name));
    }

    private String xssVulnerable() throws IOException {
        return Files.readString(new ClassPathResource("/templates" +
                "/xss-vulnerable.html").getFile().toPath(), Charset.forName("UTF-8"));
    }
}
