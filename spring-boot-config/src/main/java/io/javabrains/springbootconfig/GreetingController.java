package io.javabrains.springbootconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class GreetingController {
    @Value("${my.greeting}")
    private String greetingMessage;

    @Value("some static message")
    private String staticMessage;

    @Value("${my.val:JJDefault}")
    private String defaultValue;

    @Value("${my.list}")
    private List<String> numbers;

    @Value("#{${db.connection}}")
    private Map<String, String> dbValues;

    @Autowired
    private DbSettings dbSettings;

    @Autowired
    private Environment environment;

    @GetMapping("/greet")
    public String greeting() {
        return greetingMessage + ":" + staticMessage + ":" + defaultValue + ":" + numbers + ":" + dbValues;
    }

    @GetMapping("/config")
    public String configProperties() {
        return dbSettings.getConnection() + ":" + dbSettings.getHost() + ":" + dbSettings.getPort();
    }

    @GetMapping("/envdetails")
    public String envDetails() {
        return environment.toString();
    }
}
