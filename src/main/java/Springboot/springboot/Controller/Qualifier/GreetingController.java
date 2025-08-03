package Springboot.springboot.Controller.Qualifier;

import Springboot.springboot.Service.Qualifier.GreetManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class GreetingController {

    @Autowired
    private GreetManager greetManager;

    @GetMapping("/greet")
    public String greet(){
        return greetManager.getGreeting();
    }
}
