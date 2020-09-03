package olympix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForgotPasswordController {

    @GetMapping("/forgot_password")
    public String forgotPassword() {
        return "forgot_password";
    }

}
