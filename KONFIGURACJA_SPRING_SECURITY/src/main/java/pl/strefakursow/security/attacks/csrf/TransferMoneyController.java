package pl.strefakursow.security.attacks.csrf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class TransferMoneyController {
    @PostMapping("/secured-form/transfer")
    public ModelAndView transfer(TransferCommand command) {
        return new ModelAndView("transfer", Map.of(
                "from", command.getFromAccount(),
                "to", command.getToAccount(),
                "amount", command.getAmount()));
    }
    @GetMapping("/secured-form/transfer")
    public String transferPage(){
        return "transfer";
    }
}
