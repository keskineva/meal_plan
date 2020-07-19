package softuni.meal_plan.service;

import softuni.meal_plan.model.service.RecipeServiceModel;

import java.util.List;

public interface RecipeService {

    RecipeServiceModel addRecipe(RecipeServiceModel recipeServiceModel);

    List<RecipeServiceModel> findAllRecipes();

    RecipeServiceModel findRecipeById(String recipeId);

}
