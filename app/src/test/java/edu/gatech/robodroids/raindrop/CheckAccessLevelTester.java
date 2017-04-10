package edu.gatech.robodroids.raindrop;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sarah Storer on 4/9/2017.
 *
 * Tests the access level checker class in activity_application_main class
 */

public class CheckAccessLevelTester {

    private static final int TIMEOUT = 200;
    UserModel userManager = new UserModel("Joshua Viszlai", "123456", "password", "Manager");
    UserModel userUser = new UserModel("Sarah Storer", "234567", "password", "User");
    UserModel userAdmin = new UserModel("Joshua Jacobs", "345678", "password", "Administrator");
    UserModel userWorker = new UserModel("Sahit Schintalapudi", "456789", "password", "Worker");



    @Test(timeout = TIMEOUT)
    public void testAccessLevel() {
        assertTrue(activity_application_main.checkAccessLevel(userManager, "Manager"));
        assertTrue(activity_application_main.checkAccessLevel(userUser, "User"));
        assertTrue(activity_application_main.checkAccessLevel(userAdmin, "Administrator"));
        assertTrue(activity_application_main.checkAccessLevel(userWorker, "Worker"));

        assertFalse(activity_application_main.checkAccessLevel(userManager, "User"));
        assertFalse(activity_application_main.checkAccessLevel(userUser, "Manager"));
        assertFalse(activity_application_main.checkAccessLevel(userWorker, "Administrator"));
        assertFalse(activity_application_main.checkAccessLevel(userAdmin, "Worker"));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCheckNullUser() {
        activity_application_main.checkAccessLevel(null, "User");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCheckNullString() {
        activity_application_main.checkAccessLevel(userManager, null);
    }
}