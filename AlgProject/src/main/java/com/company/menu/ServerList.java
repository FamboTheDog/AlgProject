package com.company.menu;


import com.company.multiplayer.ServerConnection;

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
    private static TableModel tableModel;

    ServerList(ServerMenu serverMenu) {
        this.serverMenu = serverMenu;

        Object[] tableHeader = {"Name", "join"};
        Object[][] defaultData = {
                {"There are no active servers"}
        };
        tableModel = new DefaultTableModel(defaultData, tableHeader);

        JTable serverList = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        serverList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = serverList.rowAtPoint(e.getPoint());
                int col = serverList.columnAtPoint(e.getPoint());
                System.out.println(row + " " + col);
                if (col == 1) {
                    System.out.println(tableModel.getValueAt(row, 0).toString());
                    ServerConnection.joinRoom(tableModel.getValueAt(row, 0).toString());
                }
            }
        });

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
