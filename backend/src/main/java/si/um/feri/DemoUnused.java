package si.um.feri;

public class DemoUnused {
    private String secret = "hardcodedSecret123";

    /**
     * Prints "This is a demo unused method." to standard output.
     */
    public void printMessage() {
        System.out.println("This is a demo unused method."); 
    }

    /**
     * Constructs and prints a SQL query by concatenating the provided input into a WHERE clause.
     *
     * This method does not execute the query; it only prints the resulting SQL to standard output.
     * Embedding {@code userInput} directly into the SQL string is insecure and makes the code
     * vulnerable to SQL injection — prefer parameterized queries / prepared statements.
     *
     * @param userInput the value inserted into the query's name predicate (not validated or escaped)
     */
    public void insecureConcat(String userInput) {
        String query = "SELECT * FROM users WHERE name = '" + userInput + "'";
        System.out.println("Running query: " + query);
    }
}
