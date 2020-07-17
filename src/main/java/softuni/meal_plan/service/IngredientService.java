package softuni.meal_plan.service;

import softuni.meal_plan.model.entity.Ingredient;
import softuni.meal_plan.model.service.IngredientServiceModel;

import java.util.List;

public interface IngredientService {

    List<Ingredient> findAll();

    IngredientServiceModel findIngredientById(String id);

}
