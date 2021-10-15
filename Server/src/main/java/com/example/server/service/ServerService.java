package com.example.server.service;

import com.example.server.domain.Server;
import com.example.server.repository.ServersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServerService {

    private ServersRepository serverRepo;

    public Server createServer(Server server){
        return serverRepo.save(server);
    }

    public Iterable<Server> listServers() {
        return serverRepo.findAll();
    }
}
