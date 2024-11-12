import java.io.Serializable;

public class ClientIdServer implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idCounter;
    private static ClientIdServer server;

    // Private constructor for singleton pattern
    private ClientIdServer() {
        idCounter = 1; // Start ID from 1
    }

    // Public method to get the singleton instance of ClientIdServer
    public static ClientIdServer instance() {
        if (server == null) {
            server = new ClientIdServer();
        }
        return server;
    }

    // Method to generate and return the next client ID
    public int getId() {
        return idCounter++;
    }

    @Override
    public String toString() {
        return "ClientIdServer, current ID: " + idCounter;
    }
}