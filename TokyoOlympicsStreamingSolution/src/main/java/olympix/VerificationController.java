package olympix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class VerificationController {

    @GetMapping("/verification")
    public String verification() {
        return "verification";
    }

}

