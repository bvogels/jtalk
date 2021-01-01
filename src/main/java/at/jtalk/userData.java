package at.jtalk;

public class userData {

    private String userName;
    private String password;

    userData(String userName, String password){

        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }
}