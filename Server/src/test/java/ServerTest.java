import com.company.communication_protocol.user.Constants;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.server_structure.room.ActiveRooms;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerTest {

    private static final int testServerPort = 8000;

    @BeforeAll
    static void setup() {
        ServerHolder server = new ServerHolder(testServerPort);
        server.start();
    }

    private final String[] createdRooms = {"testRoom1", "testRoom2", "testRoom3", "testRoom4"};
    private final String[] shouldFail = {"testRoom1"};
    @Test
    @Order(1)
    void testRoomCreation() throws IOException {
        for (String createdRoom : createdRooms) {
            UserCommunicationProtocol.initialize(Constants.DEFAULT_IP, testServerPort);
            UserCommunicationProtocol.createRoom(createdRoom);
        }
        for (String failedRoom : shouldFail) {
            UserCommunicationProtocol.initialize(Constants.DEFAULT_IP, testServerPort);
            UserCommunicationProtocol.createRoom(failedRoom);
        }

        int activeRooms = ActiveRooms.getActiveRooms().size();
        assertEquals(createdRooms.length, activeRooms);
    }

    @Test
    @Order(2)
    void testRoomListing() {
        UserCommunicationProtocol.initialize(Constants.DEFAULT_IP, testServerPort);

        HashSet<String> set1 = new HashSet<>(List.of(createdRooms));
        HashSet<String> set2 = new HashSet<>(List.of(UserCommunicationProtocol.listRooms()));

        assertEquals(set1, set2);
    }

    @Test
    @Order(3)
    void testRoomJoining() throws IOException {
        UserCommunicationProtocol.initialize(Constants.DEFAULT_IP, testServerPort);

        String joiningTo = UserCommunicationProtocol.listRooms()[0];
        UserCommunicationProtocol.joinRoom(joiningTo);

        ArrayList<UUID> playerIDs = new ArrayList<>();
        ActiveRooms.getActiveRoomByName(joiningTo).getPlayers().forEach((socket, comm) -> playerIDs.add(comm.getId()));
        assertTrue(playerIDs.contains(UserCommunicationProtocol.getId()));
    }

}
