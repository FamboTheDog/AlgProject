package com.company.menu.server_list;

import com.company.Main;
import com.company.gameloop.GameLoop;
import com.company.menu.ServerMenu;
import com.company.multiplayer.ServerConnection;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseAdapterImpl extends MouseAdapter {

    private ServerList serverList;
    private GameLoop gameLoop;
    private ServerMenu serverMenu;

    public MouseAdapterImpl(ServerMenu serverMenu, ServerList serverList, GameLoop gameLoop) {
        this.serverList = serverList;
        this.gameLoop = gameLoop;
        this.serverMenu = serverMenu;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int row = serverList.getServerList().rowAtPoint(e.getPoint());
        int col = serverList.getServerList().columnAtPoint(e.getPoint());
        if (col == 1) {
            System.out.println(ServerList.getTableModel().getValueAt(row, 0).toString());
            ServerConnection.joinRoom(ServerList.getTableModel().getValueAt(row, 0).toString());
                Main.getViewContainer().remove(serverMenu);
                Main.getViewContainer().add(gameLoop.getGraphics());
                gameLoop.startLoop();
                Main.getViewContainer().revalidate();
                Main.getViewContainer().repaint();
        }
    }
}
