import com.company.server_structure.Server;
import com.company.server_structure.room.ActiveRooms;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.server_structure.user.ActiveUsers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    @BeforeAll
    static void setup() {
        new Thread(()->{
            try {
                ServerSocket socket = new ServerSocket(2020);
                Server server = new Server(socket);
                server.startServer();
            } catch (IOException e) {
                fail("You probably have another instance of server running on your computer!");
            }
        }).start();
    }

    @Test
    void testConnections() throws IOException {
        Socket socket = new Socket("localhost", 2020);

        assertEquals(1, ActiveUsers.getActivePlayers().size());

        socket.close();
    }

    @Test
    void testRoomCreation() throws IOException {
        UserCommunicationProtocol.initialize();

        UserCommunicationProtocol.createRoom("server");

        int activeRoom = ActiveRooms.getActiveRooms().size();
        assertEquals(1, activeRoom);
    }

}
