package com.example.server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Server {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;

    private String name;

    private String password;

    public Server(String name) {
        this.name = name;
    }

    public Server(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
