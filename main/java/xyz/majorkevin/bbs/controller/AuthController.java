package xyz.majorkevin.bbs.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.RegistrationForm;
import xyz.majorkevin.bbs.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registerUser")
    public String showRegisterPage(Model model){
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/registerUser")
    public String processRegistrationForm(@Valid RegistrationForm registerForm, Errors errors){

        if(errors.hasErrors()){
            return "register";
        }

        User dupUser = userService.findByUsername(registerForm.getUsername());
        if(dupUser != null){
            logger.info("exist: " + dupUser.getUsername());
            errors.rejectValue("username", "error.username", "User already exists");
            return "register";
        }

        User user = registerForm.toUser(passwordEncoder);
        logger.info("this user: " + user.getUsername());
//        userService.addBaseRole(user);
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }
}
