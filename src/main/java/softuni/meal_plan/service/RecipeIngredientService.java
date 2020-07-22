package softuni.meal_plan.service;

import softuni.meal_plan.model.service.RecipeIngredientServiceModel;

import java.util.List;

public interface RecipeIngredientService {

    List<RecipeIngredientServiceModel> ingredientsAndAmounts(String id);
}
