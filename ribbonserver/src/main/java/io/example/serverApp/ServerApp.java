package io.example.serverApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ServerApp.class, args);
    }
}
