package softuni.meal_plan.service;

import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;

import java.util.List;

public interface IngredientService {

    //List<Ingredient> findAll();

    IngredientServiceModel findIngredientById(String id);

    //List<IngredientServiceModel> saveIngredientList(List<String> ingredients);

    List<IngredientServiceModel> findAllIngredientsInPlannedMeals();

    IngredientServiceModel saveIngredient(String ingredient);

    RecipeIngredientServiceModel saveRecipeIngredient(RecipeIngredientServiceModel model);
}
