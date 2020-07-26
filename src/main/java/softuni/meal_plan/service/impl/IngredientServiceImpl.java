package softuni.meal_plan.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.meal_plan.model.entity.Ingredient;
import softuni.meal_plan.model.entity.RecipeIngredient;
import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;
import softuni.meal_plan.repository.IngredientRepository;
import softuni.meal_plan.repository.RecipeIngredientRepository;
import softuni.meal_plan.service.IngredientService;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, ModelMapper modelMapper, RecipeIngredientRepository recipeIngredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }


    @Override
    public List<IngredientServiceModel> findAllIngredientsInPlannedMeals() {
        return null;
    }

    @Override
    public IngredientServiceModel saveIngredient(String ingredient) {
        Ingredient ingr = this.ingredientRepository.findByName(ingredient);
        if (ingr == null) {
            ingr = this.ingredientRepository.saveAndFlush(new Ingredient(ingredient));
        }
        IngredientServiceModel model = new IngredientServiceModel();
        model.setName(ingr.getName());
        model.setId(ingr.getId());
        return model;
    }

    @Override
    public RecipeIngredientServiceModel saveRecipeIngredient(RecipeIngredientServiceModel model) {
        RecipeIngredient recipeIngredient = modelMapper.map(model, RecipeIngredient.class);
        recipeIngredient = this.recipeIngredientRepository.saveAndFlush(recipeIngredient);
        RecipeIngredientServiceModel recipeIngredientServiceModel = modelMapper.map(recipeIngredient, RecipeIngredientServiceModel.class);
        return recipeIngredientServiceModel;
    }

    @Override
    public IngredientServiceModel findIngredientById(String id) {
        return this.ingredientRepository.findById(id)
                .map(i -> this.modelMapper.map(i, IngredientServiceModel.class))
                .orElse(null);
    }
}
