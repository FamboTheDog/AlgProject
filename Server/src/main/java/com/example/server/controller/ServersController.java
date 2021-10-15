package com.example.server.controller;

import com.example.server.domain.Server;
import com.example.server.service.ServerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ServersController {

    private ServerService serverService;

    @PostMapping(value = "/create_server", consumes = "application/json")
    public Server createServer(@RequestBody Server server) {
        return serverService.createServer(server);
    }

    @GetMapping("/list_servers")
    public Iterable<Server> listServers() {
        return serverService.listServers();
    }

}
