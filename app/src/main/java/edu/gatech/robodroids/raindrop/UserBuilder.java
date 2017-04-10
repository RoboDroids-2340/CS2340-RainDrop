package edu.gatech.robodroids.raindrop;

/**
 * Created by sc on 4/10/17.
 */

public class UserBuilder {
    public UserBuilder() {

    }

    public static UserModelAssistant buildUser(String username, String id, String password,
                                               String userType) {
        String parsedId;
        if (id.contains(".")) {
            parsedId = id.replaceAll("\\.", ",");
        } else {
            parsedId = id;
        }

        String hashedPassword = hash(password);
        return new UserModelAssistant(username, parsedId, hashedPassword, userType);
    }

    public static String hash(String password) {
        return password;
    }
}
