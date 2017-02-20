package edu.gatech.robodroids.raindrop;

/**
 * Created by sc on 2/19/17.
 */

public class UserModel {
    public String name;
    public String userid;
    public String pass;

    public UserModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserModel(String name, String userid, String pass) {
        this.name = name;
        this.userid = userid;
        this.pass = pass;
    }

}
