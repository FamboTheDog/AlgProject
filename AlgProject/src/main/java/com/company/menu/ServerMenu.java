package com.company.menu;

import javax.swing.*;
import java.awt.*;

public class ServerMenu extends JPanel {

    CardLayout cards = new CardLayout();

    ServerMenu() {
        // this.add(new ServerMenuOptions());
        this.setLayout(cards);
        JPanel options = new JPanel();
        JButton createServer = new JButton("Create server");
        createServer.addActionListener(e->showCard("create"));

        JButton listServers = new JButton("Join game");
        listServers.addActionListener(e->{
            showCard("servers");
            // todo refactor serverList to have a method for loading data from api call
        });
        options.add(listServers);
        options.add(createServer);

        this.add(options, "menu");

        this.add(new ServerList(this), "servers");
        this.add(new CreateServerMenu(), "create");
        cards.show(this, "menu");
    }

    public void showCard(String cardName) {
        cards.show(this, cardName);
    }

}
