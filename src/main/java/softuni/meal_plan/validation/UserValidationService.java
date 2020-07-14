package softuni.meal_plan.validation;


import softuni.meal_plan.model.service.UserServiceModel;

public interface UserValidationService {

    boolean isUserValid(UserServiceModel userServiceModel);
}
