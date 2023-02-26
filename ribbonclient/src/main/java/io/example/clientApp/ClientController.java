package io.example.clientApp;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ClientController {

    @Autowired
    RestTemplate loadBalancedRestTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    @ApiResponse(responseCode = "200", description = "OK", content = {
        @Content(mediaType = MediaType.TEXT_PLAIN_VALUE, examples = {
            @ExampleObject(value = "Server Response:: Hello from Backend Server!!! :: Host : localhost :: Port : 9090")
        })
    })
    // @RequestMapping(value = "/client/frontend", method = RequestMethod.GET)
    @GetMapping("/client/frontend")
    public String client() {
        String response = null;

        try {
            response = loadBalancedRestTemplate.getForObject("http://ribbonserver/backend", String.class);
        } catch (Exception e) {
            log.error("Unexpected Error: ", e);
        }

        log.info("info ribbon-client, after invoking server :: {}", response);
        return "Server Response :: " + response;
    }

    @ApiResponse(responseCode = "200", description = "OK", content = {
        @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
    })
    @GetMapping("/client/uriList")
    public List<String> uriList() {
        List<String> uriList = discoveryClient.getInstances("ribbonserver").stream()
            .map(ServiceInstance::getUri)
            .map(String::valueOf)
            .collect(Collectors.toList());

        uriList.forEach(log::info);

        return uriList;
    }
}
