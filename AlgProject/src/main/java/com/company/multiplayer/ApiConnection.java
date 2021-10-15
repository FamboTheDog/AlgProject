package com.company.multiplayer;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;

public class ApiConnection {

    public static Response makeCall(String address) {
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
        return response;
    }

    public static Response makeCallWithParams(String address, Object toSend) {
        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target("http://localhost:8080/" + address);

        Invocation.Builder request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        Response response = request.post(Entity.entity(toSend, MediaType.APPLICATION_JSON_TYPE));

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            System.out.println("Success! " + response.getStatus());
        } else {
            System.out.println("ERROR! " + response.getStatus());
        }
        return response;
    }

}
