package lv.helloit_lottery.lotteryProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

}
