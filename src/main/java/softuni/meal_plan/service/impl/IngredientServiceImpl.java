package softuni.meal_plan.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.meal_plan.model.entity.Ingredient;
import softuni.meal_plan.model.entity.RecipeIngredient;
import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;
import softuni.meal_plan.repository.IngredientRepository;
import softuni.meal_plan.repository.PlannedMealRepository;
import softuni.meal_plan.repository.RecipeIngredientRepository;
import softuni.meal_plan.service.IngredientService;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final PlannedMealRepository plannedMealRepository;
    private final ModelMapper modelMapper;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, PlannedMealRepository plannedMealRepository, ModelMapper modelMapper, RecipeIngredientRepository recipeIngredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.plannedMealRepository = plannedMealRepository;
        this.modelMapper = modelMapper;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

/*    @Override
    public List<IngredientServiceModel> saveIngredientList(List<String> ingredients) {
        List<Ingredient> ingredientList = new ArrayList<>();
        for (String oneIngredient : ingredients) {
            ingredientList.add(new Ingredient(oneIngredient));
        }
        // TODO filter existing entitites!!!
        List<Ingredient> savedIngredients = this.ingredientRepository.saveAll(ingredientList);

        List<IngredientServiceModel> ingredientServiceModelList = new ArrayList<>();
        for (Ingredient oneIngr : savedIngredients) {
            ingredientServiceModelList.add(new IngredientServiceModel(oneIngr.getName()));
        }
        return ingredientServiceModelList;
    }*/

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
