package com.example.server.repository;

import com.example.server.domain.Server;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServersRepository extends CrudRepository<Server, Long> {
}
