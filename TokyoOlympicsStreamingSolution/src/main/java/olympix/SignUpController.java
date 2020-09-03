package olympix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SignUpController {

    @GetMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }

}

