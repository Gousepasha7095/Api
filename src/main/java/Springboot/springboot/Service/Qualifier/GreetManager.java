package Springboot.springboot.Service.Qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GreetManager {

    @Autowired
    @Qualifier("english")
    private EnglishGreeting englishGreeting;

    @Autowired
    @Qualifier("spanish")
    private SpansishGreeting spansishGreeting;


    public String getGreeting(){
        return spansishGreeting.greet();
    }

}
