public class DatabaseConnectionPoolManager {
    private static DatabaseConnectionPoolManager instance = null;

    private final int MAX_CONNECTIONS = 5;
    private int availableConnections;
    private int inUseConnections;

    private DatabaseConnectionPoolManager() {
        this.availableConnections = this.MAX_CONNECTIONS;
        this.inUseConnections = 0;
        System.out.println("DatabaseConnectionPoolManager created!");
    }

    public static DatabaseConnectionPoolManager getInstance() {
        if (instance == null){
            instance = new DatabaseConnectionPoolManager();
        }

        return instance;
    }

    public int getConnection() {
        if (this.availableConnections > 0){
            this.availableConnections -= 1;
            this.inUseConnections += 1;
            return inUseConnections;
        }
        throw new IllegalStateException("All connecetions in use");
    }

    public void releaseConnection(int connectionId) {
        if (this.inUseConnections > 0){
            this.availableConnections += 1;
            this.inUseConnections -= 1;
            System.out.println("Connection " + connectionId + " released");
        } else {
             System.out.print("WARNING: No connections exist");
        }

    }

    public String getStatus() {
        return String.format("Available: %d, In Use: %d", this.availableConnections, this.inUseConnections);
    }

    public static void main(String[] args) {
        System.out.println("=== DATABASE CONNECTION POOL TEST ===\n");

        // TEST 1: Lazy initialization
        System.out.println(">>> TEST 1: Lazy Initialization <<<");
        System.out.println("Before getInstance() - no instance created yet");

        DatabaseConnectionPoolManager db = DatabaseConnectionPoolManager.getInstance();
        System.out.println(db.getStatus());

        // TEST 2: Verify singleton
        System.out.println("\n>>> TEST 2: Verify Singleton <<<");
        DatabaseConnectionPoolManager db2 = DatabaseConnectionPoolManager.getInstance();
        System.out.print("Both are the same? ");
        System.out.print(db == db2);



        // TEST 3: Get connections
        System.out.println("\n>>> TEST 3: Get Connections <<<");
        try {
           db.getConnection();
           System.out.println(db.getStatus());
           db.getConnection();
           System.out.println(db.getStatus());
           db.getConnection();
           System.out.println(db.getStatus());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // TEST 4: Release connections
        System.out.println("\n>>> TEST 4: Release Connections <<<");
        db.releaseConnection(1);
        db.releaseConnection(2);
       System.out.println(db.getStatus());


        // TEST 5: Try to get too many connections
        System.out.println("\n>>> TEST 5: Exceed Connection Limit <<<");
        try {
           db.getConnection();
           db.getConnection();
           db.getConnection();
           db.getConnection();
           db.getConnection();
           db.getConnection();

        } catch (IllegalStateException e) {
            System.out.println("✓ Exception caught: " + e.getMessage());
        }

        System.out.println("\n=== TEST COMPLETE ===");
    }
}