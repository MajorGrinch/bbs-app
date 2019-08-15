package xyz.majorkevin.bbs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.majorkevin.bbs.entity.MyUserDetail;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.UpdateUserProfileForm;
import xyz.majorkevin.bbs.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/manage")
    public String showUserProfile(@AuthenticationPrincipal MyUserDetail userDetail, Model model) {
        User currUser = userDetail.getUser();
        UpdateUserProfileForm updateUserProfileForm = new UpdateUserProfileForm();
        updateUserProfileForm.setUsername(currUser.getUsername());
        updateUserProfileForm.setEmail(currUser.getEmail());
        model.addAttribute("updateUserProfileForm", updateUserProfileForm);
        return "user-profile";
    }

    @PostMapping("/manage")
    public String updateUserProfile(@Valid UpdateUserProfileForm updateUserProfileForm, Errors errors, @AuthenticationPrincipal MyUserDetail userDetail) {


        logger.info("posted updated username: |" + updateUserProfileForm.getUsername() + '|');
        User currUser = userDetail.getUser();

        if (errors.hasErrors()) {
            logger.info("submitted form has error");
            logger.info(errors.toString());
            return "user-profile";
        }

        currUser.setEmail(updateUserProfileForm.getEmail());
        currUser.setUsername(updateUserProfileForm.getUsername());

        userService.save(currUser);

        return "user-profile";
    }
}
