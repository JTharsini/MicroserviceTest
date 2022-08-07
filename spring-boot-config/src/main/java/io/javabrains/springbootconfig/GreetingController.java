package io.javabrains.springbootconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingController {
    @Value("${my.greeting}")
    private String greetingMessage;

    @Value("some static message")
    private String staticMessage;

    @Value("${my.val:JJDefault}")
    private String defaultValue;

    @Value("${my.list}")
    private List<String> numbers;

    @Value("#{${dbValues}}")
    private Map<String, String> dbValues;

    @GetMapping("/greet")
    public String greeting() {
        return greetingMessage + ":" + staticMessage + ":" + defaultValue + ":" + numbers + ":" + dbValues;
    }
}
