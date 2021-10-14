package com.company.multiplayer;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ApiConnection {

    public static void makeCall(String address) {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target("http://localhost:8080/" + address);

        Invocation.Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        Response response = request.get();

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            System.out.println("Success! " + response.getStatus());
        } else {
            System.out.println("ERROR! " + response.getStatus());
        }
        System.out.println(response.getEntity());
        System.out.println(response.readEntity(String.class));
    }

}
