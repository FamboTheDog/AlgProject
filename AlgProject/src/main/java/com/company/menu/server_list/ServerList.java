package com.company.menu.server_list;


import com.company.Main;
import com.company.gameloop.GameLoop;
import com.company.menu.ServerMenu;
import com.company.multiplayer.ServerConnection;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ServerList extends JPanel {

    private ServerMenu serverMenu;
    @Getter private static TableModel tableModel;
    @Getter private JTable serverList;

    public ServerList(ServerMenu serverMenu, GameLoop gameLoop) {
        this.serverMenu = serverMenu;

        Object[] tableHeader = {"Name", "join"};
        Object[][] defaultData = {
                {"There are no active servers"}
        };
        tableModel = new DefaultTableModel(defaultData, tableHeader);

        serverList = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        serverList.addMouseListener(new MouseAdapterImpl(serverMenu, this, gameLoop));

        this.add(new JScrollPane(serverList));
    }

    public static void loadRooms(){
        String[] servers = ServerConnection.listRooms();
        if (servers[0].equals("EMPTY")) {
            System.out.println("shit's empty");
            return;
        }

        int rowCounter = 0;
        for (String name: servers) {
            tableModel.setValueAt(name, rowCounter, 0);

            JButton joinButton = new JButton("join");
            joinButton.setActionCommand(name);
            joinButton.addActionListener(e-> ServerConnection.joinRoom(name));
            tableModel.setValueAt("Join", rowCounter, 1);

            rowCounter++;
        }
    }

}
