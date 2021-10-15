package com.company.multiplayer.api_data_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ListServers {

    private List<Server> servers;

}
