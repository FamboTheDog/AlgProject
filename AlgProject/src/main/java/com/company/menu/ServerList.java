package com.company.menu;


import com.company.multiplayer.ServerConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class ServerList extends JPanel {

    private ServerMenu serverMenu;
    private static TableModel tableModel;

    ServerList(ServerMenu serverMenu) {
        this.serverMenu = serverMenu;

        Object[] tableHeader = {"Name", "join"};
        Object[][] defaultData = {
                {"There are no active servers"}
        };
        tableModel = new DefaultTableModel(defaultData, tableHeader);

        JTable serverList = new JTable(tableModel);

        this.add(new JScrollPane(serverList));
    }

    public static void loadRooms(){
        String[] servers = ServerConnection.listRooms();
        if (servers[0].equals("EMPTY")) {
            System.out.println("shit's empty");
            return;
        }

        int rowCounter = 0, columnCounter;
        for (String name: servers) {
            columnCounter = 0;
            tableModel.setValueAt(name, rowCounter, columnCounter);
            rowCounter++;
        }
    }

}
