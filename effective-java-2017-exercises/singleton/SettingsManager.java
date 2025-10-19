public enum SettingsManager {
    INSTANCE;

    // Instance fields
    private String username;
    private String theme;
    private int fontSize;

    SettingsManager() {
        this.username = "user";
        this.theme = "Light";
        this.fontSize = 12;
    }

    public String getUsername() {
       return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        if (theme.equalsIgnoreCase("light") || theme.equalsIgnoreCase("dark")){
            this.theme = theme;
        } else {
            throw new IllegalArgumentException("Invalid theme choice");
        }
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
      this.fontSize = fontSize;
    }

    // Print all settings
    public void printSettings() {
        System.out.println("=== Application Settings ===");
        System.out.println("Username " + this.username);
        System.out.println("Theme " + this.theme);
        System.out.println("Font size " + this.fontSize);
    }

}

    class Main{
    public static void main(String[] args) {
            System.out.println("=== SETTINGS MANAGER TEST ===\n");


            System.out.println(">>> TEST 1: Access Singleton <<<");
            SettingsManager singleton = SettingsManager.INSTANCE;
            singleton.printSettings();


            // TEST 2: Modify settings
            System.out.println("\n>>> TEST 2: Modify Settings <<<");
            singleton.setUsername("Alice");
            singleton.setTheme("Dark");
            singleton.setFontSize(14);
            singleton.printSettings();


            // TEST 3: Verify singleton (access from another variable)
            System.out.println("\n>>> TEST 3: Verify Singleton Behavior <<<");
            SettingsManager singleton2 = SettingsManager.INSTANCE;
            singleton2.printSettings();
            System.out.println("Are both equal? ");
            System.out.println(  singleton == singleton2);



            System.out.println("\n=== TEST COMPLETE ===");
        }
}