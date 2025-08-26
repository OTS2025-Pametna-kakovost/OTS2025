package si.um.feri;

public class DemoUnused {
    private String secret = "hardcodedSecret123";

    /**
     * Prints a fixed demo message to standard output.
     *
     * This method is intended for demonstration and does not return a value or affect program state beyond writing to stdout.
     */
    public void printMessage() {
        System.out.println("This is a demo unused method."); 
    }

    /**
     * Constructs a SQL query by concatenating the provided input into a WHERE clause and prints it.
     *
     * <p>Uses simple string concatenation to build the query and writes the resulting SQL to standard output;
     * it does not execute the query. This approach is vulnerable to SQL injection if untrusted input is provided
     * and should be replaced with parameterized queries or prepared statements in production code.</p>
     *
     * @param userInput the value inserted into the SQL WHERE clause (treated as raw/unescaped text)
     */
    public void insecureConcat(String userInput) {
        String query = "SELECT * FROM users WHERE name = '" + userInput + "'";
        System.out.println("Running query: " + query);
    }
}
