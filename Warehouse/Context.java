
public class Context {
    private static int currentState;
    private static Warehouse warehouse;
    private static Context context;
    private int currentUser;
    private String userId;
    private static JFrame frame;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static WarehouseState[] states;
    private int[][] nextState;

    public void setLogin(int user) { currentUser = user; }

    public void setUserId(String id) { userId = id; }

    public int getLogin() { return currentUser; }
    
    public String getUserId() { return userId; }

    public JFrame getFrame() { return frame; }

    public static void main(String[] args) {

    }

}