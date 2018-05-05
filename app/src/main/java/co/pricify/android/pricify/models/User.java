package co.pricify.android.pricify.models;

public class User {

    private static final User INSTANCE = new User();
    public String userEmail = "";
    // Private constructor prevents instantiation from other classes
    private User() {}

    public static User getInstance() {
        return INSTANCE;
    }
}