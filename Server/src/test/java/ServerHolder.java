import com.company.server_structure.Server;
import com.company.server_structure.user.ActiveUsers;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.fail;

public class ServerHolder extends Thread {

    private ServerSocket socket;
    private int port;
    private Server server;

    private Logger logger = Logger.getLogger(ServerHolder.class.getName());

    public ServerHolder(int testServerPort) {
        this.port = testServerPort;
    }

    @SneakyThrows
    @Override
    public void run() {
        socket = new ServerSocket(port);
        server = new Server(socket);
        try {
            server.startServer();
        } catch (SocketException e) {
            logger.log(Level.WARNING, "Server stopped");
        }
    }

    public void closeServer() {
        this.server.setRunning(false);
        try {
            socket.close();
            this.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
