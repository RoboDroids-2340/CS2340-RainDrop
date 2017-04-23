package edu.gatech.robodroids.raindrop;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sc on 4/10/17.
 */

public class UserBuilderUnitTest {
    String username = "testusername";
    String userid = "testusername@gatech.edu";
    String pass = "password";
    String hashedPass = "password"; //currently, there is no hash being applied
    String userType = "User";

    @Test
    public void testUserBuilder() throws  Exception {
        UserModel correct =
                new UserModel(username, "testusername@gatech,edu", hashedPass, userType);
        UserModel built = UserBuilder.buildUser(username, userid, pass, userType);
        assertEquals(correct.getUserid(), built.getUserid());
        assertEquals(correct.getName(), built.getName());
        assertEquals(correct.getPass(), built.getPass());
        assertEquals(correct.getType(), built.getType());
    }

}
