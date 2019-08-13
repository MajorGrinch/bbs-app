package xyz.majorkevin.bbs.form;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.majorkevin.bbs.entity.User;

import javax.validation.constraints.NotBlank;

@Slf4j
@Data
public class RegistrationForm {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RegistrationForm.class);

    @NotBlank(message = "Username can not be blank")
    private String username;

    @NotBlank(message = "Password should not be blank")
    private String password;

    @NotBlank(message = "Confirm password should not be blank")
    private String confirm_password;

    @NotBlank(message = "Email can not be blank")
    private String email;

    public User toUser(PasswordEncoder passwordEncoder){
        logger.info("username: " + username);
        logger.info("password: " + password);
        logger.info("confirm password: " + confirm_password);
        logger.info("email: " + email);
        User user = new User(username, passwordEncoder.encode(password), (byte)1, email);
        return user;
    }

    public String getUsername() {
        return username;
    }
}
