package edu.gatech.robodroids.raindrop;

/**
 * Created by sc on 4/10/17.
 */

public class UserBuilder {
    public UserBuilder() {

    }

    public static UserModel buildUser(String username, String id, String password, String userType) {
        String parsedId = id.replaceAll("\\.", ",");
        String hashedPassword = hash(password);
        return new UserModel(username, parsedId, hashedPassword, userType);
    }

    public static String hash(String password) {
        return password;
    }
}
