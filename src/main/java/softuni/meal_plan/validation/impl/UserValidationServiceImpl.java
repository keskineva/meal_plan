package softuni.meal_plan.validation.impl;


import org.springframework.stereotype.Component;
import softuni.meal_plan.model.service.UserServiceModel;
import softuni.meal_plan.validation.UserValidationService;

@Component
public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isUserValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }
}
