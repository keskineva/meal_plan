package softuni.meal_plan.model.view;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

public class UserProfileViewModel {

    private String username;
    private String email;

    public UserProfileViewModel() {
    }

    public String getUsername() {
        return username;
    }

    @Length(min = 2, message = "Username length must be more than two characters!")
    public void setUsername(String username) {
        this.username = username;
    }

    @Email(message = "Please enter a valid email address!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
