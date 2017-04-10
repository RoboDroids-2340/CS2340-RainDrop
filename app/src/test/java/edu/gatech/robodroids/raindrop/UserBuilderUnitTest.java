package edu.gatech.robodroids.raindrop;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sc on 4/10/17.
 */

public class UserBuilderUnitTest {
    String username = "testusername";
    String userid = "testusername@gatech.edu";
    String useridNoDot = "testusername@root";
    String pass = "password";
    String hashedPass = "password"; //currently, there is no hash being applied
    String userType = "User";

    @Test
    public void testUserBuilderWithSubstitution() throws  Exception {
        UserModelAssistant correct =
                new UserModelAssistant(username, "testusername@gatech,edu", hashedPass, userType);
        UserModelAssistant built = UserBuilder.buildUser(username, userid, pass, userType);
        assertEquals(correct.userid, built.userid);
        assertEquals(correct.name, built.name);
        assertEquals(correct.pass, built.pass);
        assertEquals(correct.type, built.type);
    }

    @Test
    public void testUserBuilderWithoutSub() throws Exception {
        UserModelAssistant correct =
                new UserModelAssistant(username, "testusername@root", hashedPass, userType);
        UserModelAssistant built = UserBuilder.buildUser(username, useridNoDot, hashedPass, userType);
        assertEquals(correct.userid, built.userid);
        assertEquals(correct.name, built.name);
        assertEquals(correct.pass, built.pass);
        assertEquals(correct.type, built.type);
    }

}
