package edu.gatech.robodroids.raindrop;

/**
 * Created by sc on 2/19/17.
 */

public class UserModel {
    public String name;
    public String userid;
    public String pass;
    public String type;

    /**
     * Default constructor required for calls to DataSnapshot.getValue(User.class).
     */
    public UserModel() {

    }

    /**
     * 4 argument constructor for a user object.
     * @param name the name of the user
     * @param userid the id (email) of the user
     * @param pass the password for the account
     * @param type the permission type of user
     */
    public UserModel(String name, String userid, String pass, String type) {
        this.name = name;
        this.userid = userid;
        this.pass = pass;
        this.type = type;
    }

    /**
     * 3 argument constructor for a user object, no specified permission level.
     * @param name the name of the user
     * @param userid the id (email) of the user
     * @param pass the password for the account
     */
    public UserModel(String name, String userid, String pass) {
        this.name = name;
        this.userid = userid;
        this.pass = pass;
        this.type = "User";
    }

}
