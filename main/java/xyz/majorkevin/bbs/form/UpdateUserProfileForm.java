package xyz.majorkevin.bbs.form;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserProfileForm {

    @NotBlank(message = "Username can not be blank")
    private String username;

    @NotBlank(message = "Email can not be blank")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
