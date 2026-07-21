// The person who logs into the system to manage it (analogous to Employee).
public class Admin extends Person {
    private String username;
    private String password; // plaintext for demo purposes only
    private String designation;

    public Admin(int id, String name, String phone, String email,
                  String username, String password, String designation) {
        super(id, name, phone, email);
        this.username = username;
        this.password = password;
        this.designation = designation;
    }

    public String getUsername() { return username; }
    public boolean checkPassword(String attempt) { return password.equals(attempt); }
    public String getDesignation() { return designation; }

    @Override
    public String getRole() { return "Admin (" + designation + ")"; }
}