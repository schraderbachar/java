import java.util.ArrayList;
import java.util.List;

interface DataSource {
    List<String> getData();
}

class DatabaseDataSource implements DataSource {
    private String connectionString;

    public DatabaseDataSource(String connectionString) {
        this.connectionString = connectionString;
        System.out.println("DatabaseDataSource connected to: " + connectionString);
    }

    @Override
    public List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("Record 1");
        list.add("Record 2");
        list.add("Record 3");
        return list;
    }
}

// File data source
class FileDataSource implements DataSource {
    private String filePath;

    public FileDataSource(String filePath) {
        this.filePath = filePath;
        System.out.println("FileDataSource reading from: " + filePath);
    }

    @Override
    public List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("Line 1 from file");
        list.add("Line 2 from file");

        return list;
    }
}


class CsvDataSource implements DataSource {
    private String csvPath;

    public CsvDataSource(String csvPath) {
        this.csvPath = csvPath;
        System.out.println("CsvDataSource parsing: " + csvPath);
    }

    @Override
    public List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("Name,Age");
        list.add("Alice,30");
        list.add("Bob,25");
        return list;
    }
}

interface ReportFormatter {
    String format(List<String> data);
}

// HTML formatter
class HtmlFormatter implements ReportFormatter {
    @Override
    public String format(List<String> data) {
        // Return: "<html><body><ul><li>item1</li><li>item2</li>...</ul></body></html>"
        StringBuilder html = new StringBuilder("<html><body><ul>");
        for (int i = 0; i < data.size(); i++){
            html.append("<li>");
            html.append(data.get(i));
            html.append("</li>");
        }
        html.append("</ul></body></html>");
        return html.toString();
    }
}

// PDF formatter
class PdfFormatter implements ReportFormatter {
    @Override
    public String format(List<String> data) {

        return String.format("PDF Document:\n-----------\n%s\n-----------",data);
    }
}

// JSON formatter
class JsonFormatter implements ReportFormatter {
    @Override
    public String format(List<String> data) {
        StringBuilder json = new StringBuilder("{\"data\": [");
        for (int i = 0; i < data.size(); i++){
            json.append(String.format("%s, \";",data.get(i)));
        }
        return json.toString();
    }
}

class ReportGenerator {
    private final DataSource dataSource;      // Dependency 1
    private final ReportFormatter formatter;  // Dependency 2

    // Constructor injection with multiple dependencies
    public ReportGenerator(DataSource dataSource, ReportFormatter formatter) {
        this.dataSource = dataSource;
        this.formatter = formatter;
        System.out.println("ReportGenerator created with " +
                          dataSource.getClass().getSimpleName() + " and " +
                          formatter.getClass().getSimpleName());
    }

    public String generateReport() {
        List<String> data = this.dataSource.getData();
        return this.formatter.format(data);
    }

    public void printReport() {
       System.out.println(this.generateReport());
    }

    // Static factory method (another form of dependency injection!)
    public static ReportGenerator createDatabaseHtmlReport(String dbConnection) {
        DatabaseDataSource db = new DatabaseDataSource(dbConnection);
        HtmlFormatter html = new HtmlFormatter();

        return new ReportGenerator(db,html);
    }

    public static ReportGenerator createFileJsonReport(String filePath) {
        FileDataSource file = new FileDataSource(filePath);
        JsonFormatter json = new JsonFormatter();

        return new ReportGenerator(file,json);
    }
}

class ReportDemo {
    public static void main(String[] args) {
        System.out.println("=== REPORT GENERATOR - MULTIPLE DEPENDENCIES ===\n");

        // TEST 1: Database + HTML
        System.out.println(">>> TEST 1: Database + HTML Report <<<");
        DatabaseDataSource db = new DatabaseDataSource("testConnectionString");
        HtmlFormatter html = new HtmlFormatter();
        ReportGenerator report = new ReportGenerator(db,html);
        report.generateReport();
        report.printReport();



        // TEST 2: File + PDF
        System.out.println("\n>>> TEST 2: File + PDF Report <<<");
        FileDataSource file = new FileDataSource("./my-awesome-file");
        PdfFormatter pdf = new PdfFormatter();
        ReportGenerator report2 = new ReportGenerator(file,pdf);
        report2.generateReport();
        report2.printReport();


        // TEST 3: CSV + JSON
        System.out.println("\n>>> TEST 3: CSV + JSON Report <<<");
        CsvDataSource csv = new CsvDataSource("./my-awesome-file");
        JsonFormatter json = new JsonFormatter();
        ReportGenerator report3 = new ReportGenerator(csv,json);
        report3.generateReport();
        report3.printReport();



        // TEST 4: Using static factory methods
        System.out.println("\n>>> TEST 4: Using Factory Methods <<<");
       ReportGenerator static1 = ReportGenerator.createDatabaseHtmlReport("my-conn-string");
       static1.generateReport();
       ReportGenerator static2 = ReportGenerator.createFileJsonReport("./my-other-awesome-file");
       static2.generateReport();

        // TEST 5: Demonstrate flexibility
        System.out.println("\n>>> TEST 5: Mix and Match <<<");
        System.out.println("We can create ANY combination:");
        System.out.println("- Database + JSON");
        System.out.println("- File + HTML");
        System.out.println("- CSV + PDF");
        System.out.println("All without changing ReportGenerator code!");
        ReportGenerator report4 = new ReportGenerator(db,json);
        report4.generateReport();
        report4.printReport();


        System.out.println("\n=== TEST COMPLETE ===");
    }
}