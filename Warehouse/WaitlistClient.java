public class WaitlistClient
{
    private String clientId;
    private int quantity;

    // Constructor
    public WaitlistClient(String clientId, int quantity)
    {
        this.clientId = clientId;
        this.quantity = quantity;
    }

    // Getter for clientId
    public String getClientId()
    {
        return clientId;
    }

    // Setter for clientId
    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    // Getter for quantity
    public int getQuantity()
    {
        return quantity;
    }

    // Setter for quantity
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    // Override toString for easy display of client info
    @Override
    public String toString()
    {
        return "Client ID: " + clientId + ", Quantity: " + quantity;
    }
}