package Springboot.springboot.Service.Qualifier;

import org.springframework.stereotype.Component;

@Component("spanish")
public class SpansishGreeting implements GreetingService{
    @Override
    public String greet() {
        return "Hello This is Spanish";
    }
}
