package com.company.menu;

import com.company.Main;
import com.company.gameloop.GameLoop;
import com.company.multiplayer.ServerConnection;

import javax.swing.*;
import java.io.IOException;

public class CreateServerMenu extends JPanel {

    CreateServerMenu(GameLoop gameLoop, ServerMenu menu){
        JLabel heading = new JLabel("Create a server!");
        JLabel nameFieldText = new JLabel("Name: ");
        JTextField nameField = new JTextField();

        JButton submit = new JButton("Create server");
        submit.addActionListener(e->{
            ServerConnection.sendMessage("Server");

            Main.getViewContainer().remove(menu);
            Main.getViewContainer().add(gameLoop.getGraphics());
            gameLoop.startLoop();
            Main.getViewContainer().revalidate();
            Main.getViewContainer().repaint();
        });

        this.add(submit);
    }

}
