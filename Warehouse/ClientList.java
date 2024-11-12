import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ClientList implements Serializable {
    private static final long serialVersionUID = 1L; // For serialization compatibility
    private List<Client> clients = new LinkedList<>(); // LinkedList to hold Client objects
    private static ClientList clientList; // Singleton instance of ClientList

    // Private constructor for singleton pattern
    private ClientList() {
    }

    // Public method to get the singleton instance
    public static ClientList instance() {
        if (clientList == null) {
            clientList = new ClientList();
        }
        return clientList;
    }

    // Method to add a client to the list
    public boolean insertClient(Client client) {
        clients.add(client);
        return true;
    }

    // Method to remove a client by ID
    public boolean removeClient(String clientId) {
        Iterator<Client> iterator = clients.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getClientId().equals(clientId)) {
                iterator.remove(); // Remove the client if the ID matches
                return true; // Return true if removal was successful
            }
        }
        return false; // Return false if no matching client was found
    }

    // Method to search for a client by ID
    public Client searchClient(String clientId) {
        for (Client client : clients) {
            if (client.getClientId().equals(clientId)) {
                return client; // Return the client if found
            }
        }
        return null; // Return null if no matching client was found
    }

    // Method to return an iterator over the list of clients
    public Iterator<Client> getClients() {
        return clients.iterator();
    }

    // toString method for debugging or printing the list of clients
    @Override
    public String toString() {
        return clients.toString();
    }
}