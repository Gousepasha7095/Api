package Springboot.springboot.Service.Qualifier;

import org.springframework.stereotype.Component;

@Component("english")
public class EnglishGreeting implements GreetingService{
    @Override
    public String greet() {
        return "Heloo This is English";
    }
}
