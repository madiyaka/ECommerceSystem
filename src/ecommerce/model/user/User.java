package ecommerce.model.user;

/**
 * Base class for every person who can log in to the system.
 * It is abstract because a "plain" user never exists: every user is
 * either a Customer or an Employee.
 */
public abstract class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;

    protected User(int userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /** Compares the given password with the stored one (password is never exposed by a getter). */
    public boolean checkPassword(String candidate) {
        return password.equals(candidate);
    }

    /** Prints a one-line summary of the user. Subclasses add their own details. */
    public void displayInfo() {
        System.out.printf("#%-3d %-10s %-20s %-25s%s%n",
                userId, role, name, email, getExtraInfo());
    }

    /** Hook for subclasses to append role-specific information to displayInfo(). */
    protected abstract String getExtraInfo();

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
}
