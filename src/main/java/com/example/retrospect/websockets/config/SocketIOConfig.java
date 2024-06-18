package com.example.retrospect.websockets.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
@EnableAutoConfiguration
public class SocketIOConfig {

    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);

        config.setPingInterval(60000); // Increase to 60 seconds
        config.setPingTimeout(120000); // Increase to 120 seconds
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setBossThreads(4);
        config.setWorkerThreads(8);

        config.setAuthorizationListener(data -> {
            return true;
        });

        return new SocketIOServer(config);
    }
}
