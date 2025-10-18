import java.util.Objects;

public class Computer {
    private final   String cpu ;
    private final int ramGB; //must be at least 4
    private final int storageGB;//(must be at least 128)

    private final String gpu; //(default: "Integrated Graphics")
    private final boolean ssdStorage; //(default: false)
    private final String cpuCooler ;//(default: "Stock Cooler")
    private final int powerSupplyWatts; //(default: 500)
    private final String caseType; //(default: "Standard ATX")
    private final boolean rgbLighting; //(default: false)

    public static class Builder{

     private final   String cpu ;
    private final int ramGB; //must be at least 4
    private final int storageGB;//(must be at least 128)

    private  String gpu = "Integrated Graphics";
    private  boolean ssdStorage = false;
    private  String cpuCooler = "Stock Cooler";
    private  int powerSupplyWatts = 500;
    private  String caseType = "Standard ATX";
    private  boolean rgbLighting = false;

    public Builder(String cpu, int ramGB,int storageGB){


        if (Objects.isNull(cpu)){
            throw new IllegalArgumentException("CPU can't be null");
        }

        if (ramGB < 4){
            throw new IllegalArgumentException("Must have at least 4 GM of RAM");
        }

        if (storageGB <128){
            throw new IllegalArgumentException("Must have at least 128 GB of Storage");
        }

        if (ramGB > 32){
            throw new IllegalArgumentException("Are you sure? That's a lot of RAM!");
        }
        this.cpu = cpu;
        this.ramGB = ramGB;
        this.storageGB = storageGB;
    }

    public Builder gpu(String gpu){
            this.gpu = gpu;
            return this;
        }

        public Builder ssdStorage(boolean ssD){
            this.ssdStorage = ssD;
            return this;
        }

        public Builder cpuCooler(String cpuCooler){
            this.cpuCooler = cpuCooler;
            return this;
        }

        public Builder powerSupplyWatts(int watts){
            this.powerSupplyWatts = watts;

            if (this.powerSupplyValid()){
                return this;
            }
              throw new IllegalArgumentException("Power supply must be at least 600w with current gpu " );
        }

        public Builder caseType(String caseType){
            this.caseType = caseType;
            return this;
        }

        public Builder rgbLighting(boolean rgb){
            this.rgbLighting = rgb;
            return this;
        }


        public Computer build(){
            return new Computer(this);
        }

        private boolean powerSupplyValid(){
            if (this.gpu.compareToIgnoreCase("Integrated Graphics") != 0){
                return this.powerSupplyWatts >= 600;
            }
            return true;
        }
    }

    private Computer(Builder builder){
        this.cpu = builder.cpu;
        this.ramGB = builder.ramGB;
        this.storageGB = builder.storageGB;
        this.gpu = builder.gpu;
        this.ssdStorage = builder.ssdStorage;
        this.cpuCooler = builder.cpuCooler;
        this.powerSupplyWatts = builder.powerSupplyWatts;
        this.caseType = builder.caseType;
        this.rgbLighting = builder.rgbLighting;
    }

     public double estimatePrice() {
            double total = 0.0;
            // CPU pricing (based on common naming patterns)
            if (cpu.toLowerCase().contains("i9") || cpu.toLowerCase().contains("ryzen 9")) {
                total += 500.0;
            } else if (cpu.toLowerCase().contains("i7") || cpu.toLowerCase().contains("ryzen 7")) {
                total += 350.0;
            } else if (cpu.toLowerCase().contains("i5") || cpu.toLowerCase().contains("ryzen 5")) {
                total += 200.0;
            } else if (cpu.toLowerCase().contains("i3") || cpu.toLowerCase().contains("ryzen 3")) {
                total += 120.0;
            } else {
                total += 150.0; // default CPU price
            }

            // RAM pricing ($5 per GB)
            total += ramGB * 5.0;

            // Storage pricing
            if (ssdStorage) {
                total += storageGB * 0.15; // SSDs are $0.15 per GB
            } else {
                total += storageGB * 0.05; // HDDs are $0.05 per GB
            }

            // GPU pricing (check if it's NOT integrated graphics)
            if (!gpu.equals("Integrated Graphics")) {
                if (gpu.toLowerCase().contains("rtx 4090") || gpu.toLowerCase().contains("rx 7900 xtx")) {
                    total += 1600.0;
                } else if (gpu.toLowerCase().contains("rtx 4080") || gpu.toLowerCase().contains("rx 7900 xt")) {
                    total += 1000.0;
                } else if (gpu.toLowerCase().contains("rtx 4070") || gpu.toLowerCase().contains("rx 7800")) {
                    total += 600.0;
                } else if (gpu.toLowerCase().contains("rtx 4060") || gpu.toLowerCase().contains("rx 7700")) {
                    total += 400.0;
                } else if (gpu.toLowerCase().contains("rtx 3060") || gpu.toLowerCase().contains("rx 6600")) {
                    total += 250.0;
                } else {
                    total += 300.0; // default GPU price
                }
            }
            // Integrated graphics adds $0 (included with CPU)

            // CPU Cooler pricing
            if (cpuCooler.toLowerCase().contains("liquid") || cpuCooler.toLowerCase().contains("aio")) {
                total += 120.0;
            } else if (cpuCooler.equals("Stock Cooler")) {
                total += 0.0; // included with CPU
            } else {
                total += 40.0; // aftermarket air cooler
            }

            // Power Supply pricing (roughly $0.15 per watt)
            total += powerSupplyWatts * 0.15;

            // Case pricing
            if (caseType.toLowerCase().contains("premium") || caseType.toLowerCase().contains("gaming tower")) {
                total += 150.0;
            } else if (caseType.equals("Standard ATX")) {
                total += 60.0;
            } else if (caseType.toLowerCase().contains("mini") || caseType.toLowerCase().contains("itx")) {
                total += 80.0;
            } else {
                total += 70.0;
            }

            // RGB Lighting (adds premium)
            if (rgbLighting) {
                total += 50.0;
            }

            return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════╗\n");
        sb.append("║       COMPUTER CONFIGURATION          ║\n");
        sb.append("╚════════════════════════════════════════╝\n");
        sb.append("\n┌─ Core Components ─────────────────────┐\n");
        sb.append(String.format("│ CPU: %-32s │\n", cpu));
        sb.append(String.format("│ RAM: %-32s │\n", ramGB + " GB"));
        sb.append(String.format("│ Storage: %-28s │\n", storageGB + " GB " + (ssdStorage ? "(SSD)" : "(HDD)")));
        sb.append(String.format("│ GPU: %-32s │\n", gpu));
        sb.append("└───────────────────────────────────────┘\n");

        sb.append("\n┌─ Additional Components ───────────────┐\n");
        sb.append(String.format("│ CPU Cooler: %-25s │\n", cpuCooler));
        sb.append(String.format("│ Power Supply: %-23s │\n", powerSupplyWatts + "W"));
        sb.append(String.format("│ Case: %-31s │\n", caseType));
        sb.append(String.format("│ RGB Lighting: %-23s │\n", rgbLighting ? "Yes" : "No"));
        sb.append("└───────────────────────────────────────┘\n");

        sb.append(String.format("\n💰 Estimated Price: $%.2f\n", estimatePrice()));

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("COMPUTER BUILDER - SPECIFICATION TEST");
        System.out.println("=".repeat(50));

        // TEST 1: Budget Computer (minimal specs)
        System.out.println("\n>>> TEST 1: Budget Computer <<<");
        try {
            Computer budget = new Computer.Builder("Intel Core i3-12100", 8, 256)
                    .build();
            System.out.println(budget);
            System.out.println("✓ Budget computer created successfully!");
        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        // TEST 2: Gaming Computer (high-end GPU, lots of RAM, RGB)
        System.out.println("\n>>> TEST 2: Gaming Computer <<<");
        try {
            Computer gaming = new Computer.Builder("Intel Core i7-13700K", 32, 1000)
                    .gpu("NVIDIA RTX 4080")
                    .ssdStorage(true)
                    .cpuCooler("Liquid Cooling")
                    .powerSupplyWatts(850)
                    .caseType("Gaming Tower")
                    .rgbLighting(true)
                    .build();
            System.out.println(gaming);
            System.out.println("✓ Gaming computer created successfully!");
        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        // TEST 3: Invalid Computer - High-end GPU with low power supply (SHOULD FAIL)
        System.out.println("\n>>> TEST 3: Invalid Computer (GPU without enough power) <<<");
        try {
            Computer invalid = new Computer.Builder("Intel Core i9-13900K", 16, 512)
                    .gpu("NVIDIA RTX 4090")
                    .powerSupplyWatts(500)  // Too low for this GPU!
                    .build();
            System.out.println(invalid);
            System.out.println("✗ FAILED: Should have thrown an exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Exception caught as expected: " + e.getMessage());
        }

        // TEST 4: Validation - RAM too low (SHOULD FAIL)
        System.out.println("\n>>> TEST 4: Invalid RAM (less than 4GB) <<<");
        try {
            Computer invalidRAM = new Computer.Builder("Intel Core i5-12400", 2, 256)
                    .build();
            System.out.println(invalidRAM);
            System.out.println("✗ FAILED: Should have thrown an exception for low RAM!");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Exception caught as expected: " + e.getMessage());
        }

        // TEST 5: Validation - Storage too low (SHOULD FAIL)
        System.out.println("\n>>> TEST 5: Invalid Storage (less than 128GB) <<<");
        try {
            Computer invalidStorage = new Computer.Builder("AMD Ryzen 5 5600", 16, 64)
                    .build();
            System.out.println(invalidStorage);
            System.out.println("✗ FAILED: Should have thrown an exception for low storage!");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Exception caught as expected: " + e.getMessage());
        }

        // TEST 6: Validation - Excessive RAM warning (SHOULD FAIL with custom message)
        System.out.println("\n>>> TEST 6: Excessive RAM (more than 32GB) <<<");
        try {
            Computer excessiveRAM = new Computer.Builder("AMD Ryzen 9 7950X", 64, 2000)
                    .ssdStorage(true)
                    .build();
            System.out.println(excessiveRAM);
            System.out.println("✗ FAILED: Should have thrown an exception for excessive RAM!");
        } catch (Exception e) {
            System.out.println("✓ Exception caught as expected: " + e.getMessage());
        }

        // TEST 7: Valid high-end GPU with sufficient power supply
        System.out.println("\n>>> TEST 7: Valid High-End Computer <<<");
        try {
            Computer highEnd = new Computer.Builder("AMD Ryzen 9 7950X3D", 32, 2000)
                    .gpu("NVIDIA RTX 4090")
                    .ssdStorage(true)
                    .cpuCooler("AIO Liquid Cooler")
                    .powerSupplyWatts(1000)  // Sufficient power!
                    .caseType("Premium Full Tower")
                    .rgbLighting(true)
                    .build();
            System.out.println(highEnd);
            System.out.println("✓ High-end computer created successfully!");
        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        // TEST 8: Integrated graphics with default power supply (SHOULD WORK)
        System.out.println("\n>>> TEST 8: Integrated Graphics (default values) <<<");
        try {
            Computer integrated = new Computer.Builder("Intel Core i5-13400", 16, 512)
                    .ssdStorage(true)
                    // Not setting GPU - should default to "Integrated Graphics"
                    // Not setting power supply - should default to 500W
                    .build();
            System.out.println(integrated);
            System.out.println("✓ Integrated graphics computer created successfully!");
        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        // TEST 9: Test immutability (no setters should exist)
        System.out.println("\n>>> TEST 9: Immutability Check <<<");
        try {
            Computer testComputer = new Computer.Builder("Intel Core i5-12400", 16, 512).build();
            // Try to call a setter (this should cause a compile error if done correctly)
            // testComputer.setCpu("Different CPU");  // Uncomment to test - should not compile!
            System.out.println("✓ Computer class appears to be immutable (no setters available)");
        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        // TEST 10: Builder method chaining
        System.out.println("\n>>> TEST 10: Builder Method Chaining <<<");
        try {
            Computer chained = new Computer.Builder("Intel Core i7-12700", 16, 1000)
                    .ssdStorage(true)
                    .gpu("NVIDIA RTX 4070")
                    .powerSupplyWatts(750)
                    .rgbLighting(true)
                    .cpuCooler("Noctua NH-D15")
                    .caseType("Mid Tower")
                    .build();
            System.out.println("✓ Method chaining works correctly!");
            System.out.println("   Price: $" + String.format("%.2f", chained.estimatePrice()));
        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("TESTING COMPLETE!");
        System.out.println("=".repeat(50));
    }


}
