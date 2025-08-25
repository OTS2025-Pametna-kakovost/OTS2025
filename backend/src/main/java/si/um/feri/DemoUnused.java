package si.um.feri;

public class DemoUnused {
    private String secret = "hardcodedSecret123";

    public void printMessage() {
        System.out.println("This is a demo unused method."); 
    }

    public void insecureConcat(String userInput) {
        String query = "SELECT * FROM users WHERE name = '" + userInput + "'";
        System.out.println("Running query: " + query);
    }
}
