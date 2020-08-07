package softuni.meal_plan.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import softuni.meal_plan.model.service.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUserName(String username);

    UserServiceModel findUserById(String id);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

    List<UserServiceModel> findAllUsers();

    void deleteUser(String id);

    void makeAdmin(String id);

    void makeUser(String id);

    UserServiceModel findByUsername(String username);

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel findUserByEmail(String email);
}
