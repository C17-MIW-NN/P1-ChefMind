package nl.miwnn.ch17.codalabs.chefmind.dto;

/**
 * @author Nelleke Jansen
 * Data transfer object for new ChefMind users.
 */
public class NewChefMindUserDTO {
    private String username;
    private String password;
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
