package io.javabrains.springbootconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration // now this class is a bean
@ConfigurationProperties("db")
public class DbSettings {
    private String connection;
    private String host;
    private int port;
    private int dummyPort;

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDummyPort() {
        return dummyPort;
    }

    public void setDummyPort(int dummyPort) {
        this.dummyPort = dummyPort;
    }
}
