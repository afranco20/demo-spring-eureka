package io.example.serverApp;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ServerController {
    @Autowired
    Environment environment;

    @ApiResponse(responseCode = "200", description = "OK", content = {
        @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, examples = {
            @ExampleObject(value = "Hello from Server :: Host : localhost :: Port : 9090")
        })
    })
    @GetMapping("/backend")
    public String backend() {
        String serverPort = environment.getProperty("local.server.port");

        log.info("Server Port : {}", serverPort);

        return "Hello from Server :: Host : localhost :: Port : " + serverPort;
    }
}
