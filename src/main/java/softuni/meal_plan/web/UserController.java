package softuni.meal_plan.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.meal_plan.model.binding.UserEditBindingModel;
import softuni.meal_plan.model.binding.UserLoginBindingModel;
import softuni.meal_plan.model.binding.UserRegisterBindingModel;
import softuni.meal_plan.model.service.RoleServiceModel;
import softuni.meal_plan.model.service.UserServiceModel;
import softuni.meal_plan.model.view.UserProfileViewModel;
import softuni.meal_plan.service.UserService;
import softuni.meal_plan.web.annotations.PageTitle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Register")
    public ModelAndView register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return super.view("user/register");
    }

    @PostMapping("/register")
    //@ModelAttribute //this is mapping the fields automatically
    //BindingResult should be always after the modelatrribute that it is validating
    //RedirectAttributes for when we need to redirect
    public String registerConfirm(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //in the context of this controller we redirect to the register page.
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "registerPasswordsNotEquals", "Passwords are not equals!");
            return "user/register";
        }

        try {
            UserServiceModel userExists = this.userService.findUserByEmail(userRegisterBindingModel.getEmail());
            if (null != userExists) {
                bindingResult.rejectValue("email", "registerEmailAlreadyExists", "Email already taken!");
                return "user/register";
            }
        } catch (UsernameNotFoundException e) {
            // email is free, no worries, proceed!
        }

        try {
            UserServiceModel userExists = this.userService.findUserByUserName(userRegisterBindingModel.getUsername());
            if (null != userExists) {
                bindingResult.rejectValue("username", "registerUsernameAlreadyExists", "Username already taken!");
                return "user/register";
            }
        } catch (UsernameNotFoundException e) {
            // username is free, no worries, proceed!
        }

        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return "redirect:login";
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public String login(Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
            model.addAttribute("notFound", false);
        }
        return "user/login";
    }

    @GetMapping(value = "/login", params = {"error"})
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public String loginError(HttpServletRequest request, Model model) {
        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
            model.addAttribute("notFound", false);
        }

        String errorMessage = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
            Exception ex = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (ex != null) {
                if (ex instanceof BadCredentialsException) {
                    errorMessage = "Username or password is incorrect";
                } else {
                    errorMessage = ex.getMessage();
                }
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "user/login";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Profile")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView){
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));
        return super.view("user/profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit User")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView) {
        modelAndView
                .addObject("model", this.modelMapper.map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));

        return super.view("user/edit-profile", modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(@ModelAttribute UserEditBindingModel model){
        if (!model.getPassword().equals(model.getConfirmPassword())){
            return super.view("user/edit-profile");
        }

        this.userService.editUserProfile(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());

        return super.redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All users")
    public ModelAndView showAllUsers(ModelAndView modelAndView){
        List<UserServiceModel> users = this.userService.findAllUsers()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());

        Map<String, Set<RoleServiceModel>> userAndAuthorities = new HashMap<>();
        users.forEach(u -> userAndAuthorities.put(u.getId(), u.getAuthorities()));

        modelAndView.addObject("users", users);
        modelAndView.addObject("usersAndAuths", userAndAuthorities);
        return super.view("user/all-users", modelAndView);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Delete User")
    public ModelAndView deleteUser(@PathVariable String id, ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.findUserById(id);

        modelAndView.addObject("user", userServiceModel);
        modelAndView.addObject("userId", id);

        return super.view("user/delete-user", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteUserConfirm(@PathVariable String id) {
        this.userService.deleteUser(id);

        return super.redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdminRole(@PathVariable String id) {
        this.userService.makeAdmin(id);

        return super.redirect("/users/all");
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView setUserRole(@PathVariable String id) {
        this.userService.makeUser(id);

        return super.redirect("/users/all");
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
