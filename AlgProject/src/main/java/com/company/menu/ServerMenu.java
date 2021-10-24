package com.company.menu;

import com.company.gameloop.GameLoop;
import com.company.menu.server_list.ServerList;

import javax.swing.*;
import java.awt.*;

public class ServerMenu extends JPanel {

    CardLayout cards = new CardLayout();

    public ServerMenu(GameLoop gameLoop) {
        // this.add(new ServerMenuOptions());
        this.setLayout(cards);
        JPanel options = new JPanel();
        JButton createServer = new JButton("Create server");
        createServer.addActionListener(e->showCard("create"));

        JButton listServers = new JButton("Join game");
        listServers.addActionListener(e->{
            showCard("servers");
            ServerList.loadRooms();
        });
        options.add(listServers);
        options.add(createServer);

        this.add(options, "menu");

        this.add(new ServerList(this, gameLoop), "servers");
        this.add(new CreateServerMenu(gameLoop, this), "create");
        cards.show(this, "menu");
    }

    public void showCard(String cardName) {
        cards.show(this, cardName);
    }

}
