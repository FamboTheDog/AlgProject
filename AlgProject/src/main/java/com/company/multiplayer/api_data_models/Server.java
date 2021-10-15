package com.company.multiplayer.api_data_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
public class Server {
    private Long id;
    private String name;
    private String password;

    public Server(String name) {
        this.name = name;
    }

}
