public class GameStateManager {
    // Private static VOLATILE instance (volatile is crucial for thread safety)
    private static volatile GameStateManager instance = null;

    private int score;
    private int level;
    private int lives;
    private boolean gameOver;

    private GameStateManager() {
        this.score = 0;
        this.level = 1;
        this.lives = 3;
        this.gameOver = false;
        System.out.println("GameStateManager created by thread: " +
                          Thread.currentThread().getName());
    }

    // Double-checked locking for thread-safe lazy initialization
    public static GameStateManager getInstance() {
        if (instance == null) {
            synchronized (GameStateManager.class) {
                if (instance == null) {
                    instance = new GameStateManager();
                }
            }
        }
        return instance;
    }

    // Synchronized method to add score
    public synchronized void addScore(int points) {
        this.score += points;
        System.out.println("Score updated by thread: " +
                          Thread.currentThread().getName() + " + " + points + " = " + this.score);
    }

    // Synchronized method to level up
    public synchronized void levelUp() {
        this.level += 1;
         System.out.println("Level up by thread: " +
                          Thread.currentThread().getName() + " Now Level " + this.level);
    }

    // Synchronized method to lose a life
    public synchronized void loseLife() {
        this.lives -= 1;
        if (lives == 0){
            this.gameOver = true;
        }
       System.out.println(this.getGameState());
    }

    public synchronized String getGameState() {
        return String.format("Score: %d, Level: %d, Lives: %d, Game Over: %s",
                           score, level, lives, gameOver);
    }

    public synchronized int getScore() {
        return score;
    }

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public static void main(String[] args) {
        System.out.println("=== GAME STATE MANAGER (THREAD-SAFE) TEST ===\n");

        // TEST 1: Single-threaded access
        System.out.println(">>> TEST 1: Single-threaded Access <<<");
        GameStateManager game = GameStateManager.getInstance();
        game.getGameState();


        // TEST 2: Multi-threaded singleton creation
        System.out.println("\n>>> TEST 2: Multi-threaded Singleton Creation <<<");
        System.out.println("Creating 5 threads that all try to get instance...");

        Runnable task = () -> {
            GameStateManager manager = GameStateManager.getInstance();
            System.out.println(Thread.currentThread().getName() +
                             " got instance: " + manager);
        };

        for (int i = 0; i < 5; i++) {
            new Thread(task, "Thread-" + i).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Check: Only ONE 'GameStateManager created' message should appear!");


        System.out.println("\n>>> TEST 3: Multi-threaded State Updates <<<");

         Runnable task1= () -> {
            GameStateManager manager = GameStateManager.getInstance();
            System.out.println(Thread.currentThread().getName() +" got instance: " + manager);
            manager.addScore(100);
        };

         Runnable task2= () -> {
            GameStateManager manager = GameStateManager.getInstance();
            System.out.println(Thread.currentThread().getName() +" got instance: " + manager);
            manager.levelUp();
        };

        for (int i = 0; i < 3; i++) {
            new Thread(task1, "Thread-" + i).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(task2, "Thread-" + i).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(game.getGameState());


        // TEST 4: Verify singleton across threads
        System.out.println("\n>>> TEST 4: Verify Singleton Behavior <<<");


        GameStateManager verifyManager = GameStateManager.getInstance();

        // Print the state - it should show all the updates from the threads
        System.out.println("Final game state: " + verifyManager.getGameState());

        // Verify the scores were accumulated from all threads
        System.out.println("\nVerification:");
        System.out.println("- If 3 threads each added 100 points, score should be 300");
        System.out.println("- If 2 threads each leveled up, level should be 3 (started at 1)");
        System.out.println("- Actual score: " + verifyManager.getScore());


        System.out.println("\n=== TEST COMPLETE ===");
    }
}