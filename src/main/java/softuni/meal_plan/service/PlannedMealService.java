package softuni.meal_plan.service;

import softuni.meal_plan.model.service.PlannedMealServiceModel;

import java.util.List;

public interface PlannedMealService {

   PlannedMealServiceModel addMealToPlan(PlannedMealServiceModel plannedMealServiceModel);

   List<PlannedMealServiceModel> findAllPlannedMeals();



}
