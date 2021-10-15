package com.company.menu;

import com.company.multiplayer.ApiConnection;
import com.company.multiplayer.api_data_models.Server;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.ws.rs.core.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ServerList extends JPanel {

    ServerList(ServerMenu serverMenu) {
        Response res = ApiConnection.makeCall("list_servers");
        String output = res.readEntity(String.class);
        Type objectType = new TypeToken<ArrayList<Server>>() {}.getType();

        Gson gson = new Gson();
        List<Server> servers = gson.fromJson(output, objectType);
        servers.forEach(s-> System.out.println(s.getName()));

        String[] tableHeader = {"Name", "join"};
        TableModel tableModel = new DefaultTableModel(tableHeader, servers.size());
        int rowCounter = 0;
        int columnCounter;
        for (Server server: servers) {
            columnCounter = 0;
            tableModel.setValueAt(server.getName(), rowCounter, columnCounter);
            rowCounter++;
        }

        JTable serverList = new JTable(tableModel);

        JButton createServer = new JButton("Create server");
        createServer.addActionListener(e->{
            serverMenu.showCard("create");
        });

        this.add(new JScrollPane(serverList));
    }

}
