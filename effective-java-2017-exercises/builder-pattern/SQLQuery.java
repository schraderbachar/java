import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SQLQuery {
    private final String tableName;
    private final List<String> selectColumns;
    private final String whereClause;
    private final String orderBy;
    private final String groupBy;
    private final String having;
    private final Integer limit;
    private final boolean distinct;

    private SQLQuery(Builder builder) {
        this.tableName = builder.tableName;
        this.selectColumns = builder.selectColumns;
        this.whereClause = builder.whereClause;
        this.orderBy = builder.orderBy;
        this.groupBy = builder.groupBy;
        this.having = builder.having;
        this.limit = builder.limit;
        this.distinct = builder.distinct;
    }

  public String toSQL() {
    StringBuilder sql = new StringBuilder("SELECT ");

    if (this.distinct) {
        sql.append("DISTINCT ");
    }

    if (this.selectColumns == null || this.selectColumns.isEmpty()) {
        sql.append("* ");
    } else {
        sql.append(String.join(", ", this.selectColumns)).append(" ");
    }

    sql.append("FROM ").append(this.tableName);

    if (this.whereClause != null) {
        sql.append(" WHERE ").append(this.whereClause);
    }

    if (this.groupBy != null) {
        sql.append(" GROUP BY ").append(this.groupBy);
    }

    if (this.having != null) {
        sql.append(" HAVING ").append(this.having);
    }

    if (this.orderBy != null) {
        sql.append(" ORDER BY ").append(this.orderBy);
    }

    if (this.limit != null) {
        sql.append(" LIMIT ").append(this.limit);
    }

    return sql.toString();
}

    @Override
    public String toString() {
        return toSQL();
    }

    public static class Builder {
        private final String tableName;

        private List<String> selectColumns = null;
        private String whereClause = null;
        private String orderBy = null;
        private String groupBy = null;
        private String having = null;
        private Integer limit = null;
        private boolean distinct = false;

        public Builder(String tableName) {
            if (Objects.isNull(tableName)){
                throw new IllegalArgumentException("Table name must not be empty");
            }
            this.tableName = tableName;
        }

        public Builder select(String... columns) {
            this.selectColumns = Arrays.asList(columns);
            return this;
        }

        public Builder where(String condition) {
            this.whereClause = condition;
            return this;
        }

        public Builder orderBy(String column) {
            this.orderBy = column;
            return this;
        }

        public Builder groupBy(String column) {
            this.groupBy = column;
            return this;
        }

        public Builder having(String condition) {
            this.having = condition;
            return this;
        }

        public Builder limit(int n) {
            this.limit = n;
            return this;
        }

        public Builder distinct() {
            this.distinct = true;
            return this;
        }

        public SQLQuery build() {
            if (Objects.nonNull(this.having) && Objects.isNull(this.groupBy)){
                throw new IllegalArgumentException("HAVING can only exist if GROUP BY is set");
            }

            return new SQLQuery(this);
        }
    }

    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("SQL QUERY BUILDER - TEST");
        System.out.println("=".repeat(50));

        // TEST 1: Simple query - SELECT * FROM users
        System.out.println("\n>>> TEST 1: Simple Query <<<");
        try {
            SQLQuery simple = new SQLQuery.Builder("users").build();
            System.out.println(simple.toSQL());
            System.out.println("✓ Simple query created successfully!");
        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        // TEST 2: Complex query with DISTINCT, WHERE, ORDER BY, LIMIT
        System.out.println("\n>>> TEST 2: Complex Query <<<");
        try {
            SQLQuery complexQuery = new SQLQuery.Builder("users").where("AGE NOT NULL").distinct().orderBy("NAME").limit(3).build();
            System.out.println(complexQuery.toSQL());
            System.out.println("✓ Complex query created successfully!");

        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        // TEST 3: Aggregation query with GROUP BY and HAVING
        System.out.println("\n>>> TEST 3: Aggregation Query <<<");
        try {
            SQLQuery aggregation = new SQLQuery.Builder("employees")
            .select("department", "COUNT(*)")
            .groupBy("department")
            .having("COUNT(*) > 5")
            .build();
            System.out.println(aggregation);
            System.out.println("✓ Aggregation query created successfully!");

        } catch (Exception e) {
            System.out.println("✗ FAILED: " + e.getMessage());
        }

        // TEST 4: Invalid query - HAVING without GROUP BY (SHOULD FAIL)
        System.out.println("\n>>> TEST 4: Invalid Query (HAVING without GROUP BY) <<<");
        try {
            SQLQuery aggregation = new SQLQuery.Builder("employees")
            .select("department", "COUNT(*)")
            .having("COUNT(*) > 5")
            .build();
            System.out.println(aggregation);
            System.out.println("✓ Aggregation query created successfully!");

        } catch (IllegalArgumentException e) {
            System.out.println("✓ Exception caught as expected: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("TESTING COMPLETE!");
        System.out.println("=".repeat(50));
    }
}