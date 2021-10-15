package com.company.menu;

import com.company.multiplayer.ApiConnection;
import com.company.multiplayer.api_data_models.Server;

import javax.swing.*;

public class CreateServerMenu extends JPanel {

    CreateServerMenu(){
        JLabel heading = new JLabel("Create a server!");
        JLabel nameFieldText = new JLabel("Name: ");
        JTextField nameField = new JTextField();

        JButton submit = new JButton("Create server");
        submit.addActionListener(e->{
            ApiConnection.makeCallWithParams("create_server", new Server("Fuck yeah!"));

        });

        this.add(submit);
    }

}
