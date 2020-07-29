package softuni.meal_plan.service;

import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;

public interface IngredientService {

    IngredientServiceModel saveIngredient(String ingredient);
    RecipeIngredientServiceModel saveRecipeIngredient(RecipeIngredientServiceModel model);
    IngredientServiceModel findIngredientById(String id);
}
