import java.util.List;

public class Login {
    private List<Admin> admins;
    private Admin loggedInAdmin;

    public Login(List<Admin> admins) { this.admins = admins; }

    public boolean authenticate(String username, String password) {
        for (Admin a : admins) {
            if (a.getUsername().equals(username) && a.checkPassword(password)) {
                loggedInAdmin = a;
                return true;
            }
        }
        return false;
    }

    public Admin getLoggedInAdmin() { return loggedInAdmin; }
}