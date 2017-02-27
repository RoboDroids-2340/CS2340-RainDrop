package edu.gatech.robodroids.raindrop;

/**
 * Created by sc on 2/19/17.
 */

public class UserModel {
    public String name;
    public String userid;
    public String pass;
    public String type;

    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserModel(String name, String userid, String pass, String type) {
        this.name = name;
        this.userid = userid;
        this.pass = pass;
        this.type = type;
    }


    public UserModel(String name, String userid, String pass) {
        this.name = name;
        this.userid = userid;
        this.pass = pass;
        this.type = "User";
    }

}
