package edu.gatech.robodroids.raindrop;

/**
 * Created by RoboDroids.
 */

public class UserBuilder {
    /**
     *
     * @param username username
     * @param id id
     * @param password password
     * @param userType user type
     * @return UserModel to be built
     */
    public static UserModel buildUser(String username, String id, String password, String userType){
        String parsedId = id.replaceAll("\\.", ",");
        String hashedPassword = hash(password);
        return new UserModel(username, parsedId, hashedPassword, userType);
    }

    /**
     *
     * @param password password to has
     * @return hashed password
     */
    private static String hash(String password) {
        return password;
    }
}
