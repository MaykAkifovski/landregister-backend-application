package de.htw.berlin.landregisterbackendapplication.hyperledger.user;

public class UserContextDB {

    public static UserContext adminContext = null;
    public static UserContext userContext = null;

    public static boolean areUsersNotCreated() {
        return adminContext == null || userContext == null;
    }
}
