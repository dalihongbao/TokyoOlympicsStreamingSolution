package olympix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmailConfirmationController {

    @GetMapping("/email_confirmed")
    public String confirmation() {
        return "email_Confirmed";
    }

}
