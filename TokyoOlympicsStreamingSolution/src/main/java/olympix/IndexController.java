package olympix;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static olympix.Application.loggedIn;
import static olympix.Application.username;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("username", username);
        model.addAttribute("loggedIn", loggedIn);
        return "index";
    }
}
