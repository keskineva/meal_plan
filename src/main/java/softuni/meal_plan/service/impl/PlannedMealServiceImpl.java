package softuni.meal_plan.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import softuni.meal_plan.model.entity.PlannedMeal;
import softuni.meal_plan.model.entity.Recipe;
import softuni.meal_plan.model.entity.User;
import softuni.meal_plan.model.service.PlannedMealServiceModel;
import softuni.meal_plan.repository.PlannedMealRepository;
import softuni.meal_plan.repository.RecipeRepository;
import softuni.meal_plan.repository.UserRepository;
import softuni.meal_plan.service.PlannedMealService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlannedMealServiceImpl implements PlannedMealService {

    private final ModelMapper modelMapper;
    private final PlannedMealRepository plannedMealRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;


    @Autowired
    public PlannedMealServiceImpl(ModelMapper modelMapper, PlannedMealRepository plannedMealRepository, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.plannedMealRepository = plannedMealRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }


    @Override
    public PlannedMealServiceModel addMealToPlan(PlannedMealServiceModel plannedMealServiceModel) {
        PlannedMeal plannedMeal = this.modelMapper.map(plannedMealServiceModel, PlannedMeal.class);

        plannedMeal.setRecipe(this.modelMapper.map(plannedMealServiceModel.getRecipe(), Recipe.class));
        plannedMeal.setPlannedDateTime(plannedMealServiceModel.getPlannedDateTime());
        plannedMeal.setPlannedPortionsCount(plannedMealServiceModel.getPlannedPortionsCount());
        User user = userRepository.findByUsername(plannedMealServiceModel.getUser().getUsername()).get();
        plannedMeal.setUser(user);

        return this.modelMapper.map(this.plannedMealRepository.saveAndFlush(plannedMeal), PlannedMealServiceModel.class);

    }

    @Override
    public List<PlannedMealServiceModel> findAllPlannedMealsByUsername() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return this.plannedMealRepository.findPlannedMealsByUser_Username_OrderByPlannedDateTimeAsc(username)
                .stream()
                .map(r -> this.modelMapper.map(r, PlannedMealServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePlannedMeal(String plannedMealId) {
        this.plannedMealRepository.deleteById(plannedMealId);
        this.plannedMealRepository.flush();
    }


}
