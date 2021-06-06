package utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class DatabaseCreds {

    private String url = "";
    private String username = "";
    private String password = "";

    public String getUrl() {
        return url;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void read_credentials(){
        try {
            File myObj = new File("soso");
            Scanner myReader = new Scanner(myObj);
            this.url = myReader.nextLine();
            this.username = myReader.nextLine();
            this.password = myReader.nextLine();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading credentials in utils/Credentials.");
            e.printStackTrace();
        }
    }
}
