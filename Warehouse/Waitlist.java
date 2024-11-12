import java.io.*;
import java.util.*;

public class Waitlist {
    
    private List<WaitlistClient> list = new LinkedList<>();

    public Waitlist(){
    }

    public Waitlist(Iterator<WaitlistClient> copyList) {
        while(copyList.hasNext()) {
            list.add(copyList.next());
        }
    }

    public Iterator<WaitlistClient> getWaitlist() {
        return list.iterator();
    }

    public boolean addClient(String clientId, int quantity) {
        WaitlistClient client = new WaitlistClient(clientId, quantity);
        list.add(client);
        return true;
    }

    public boolean removeClient(String clientId) {
        Iterator<WaitlistClient> iterator = list.iterator();
        while(iterator.hasNext()) {
            WaitlistClient client = iterator.next();
            if(client.getClientId() == clientId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean setQuantity(String clientId, int quantity) {
        Iterator<WaitlistClient> iterator = list.iterator();
        while(iterator.hasNext()) {
            WaitlistClient client = iterator.next();
            if(client.getClientId() == clientId) {
                client.setQuantity(quantity);
                return true;
            }
        }
        return false;
    }
}