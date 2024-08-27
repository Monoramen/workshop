package com.workskop.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import com.workskop.config.AuthenticationRequest;
import com.workskop.dto.UserDto;
import com.workskop.entity.User;
import com.workskop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
public class AuthController {

    private UserService userService;
    private Authentication authentication;

    public AuthController(UserService userService) {
        this.userService = userService;

    }

    // handler method to handle home page request
    @GetMapping("/")
    public String home(Model model){
        return "index";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "users";
    }


    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login")
    public String login(@RequestParam(name = "loginRequired", required = false) final Boolean loginRequired,
                        @RequestParam(name = "loginError", required = false) final Boolean loginError,
                        @RequestParam(name = "logoutSuccess", required = false) final Boolean logoutSuccess,
                        final Model model,
                        Locale locale) {
        model.addAttribute("authentication", new AuthenticationRequest());
        if (loginRequired == Boolean.TRUE) {
            model.addAttribute("infoMessage", messageSource.getMessage("authentication.login.required", null, locale));
        }
        if (loginError == Boolean.TRUE) {
            model.addAttribute("errorMessage", messageSource.getMessage("authentication.login.error", null, locale));
        }
        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute("infoMessage", messageSource.getMessage("authentication.logout.success", null, locale));
        }
        return "login";
    }


    @ModelAttribute("requestUri")
    String getRequestServletPath(final HttpServletRequest request) {
        return request.getRequestURI();
    }

}
