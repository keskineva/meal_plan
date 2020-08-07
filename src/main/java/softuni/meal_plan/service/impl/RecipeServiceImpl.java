package softuni.meal_plan.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.meal_plan.error.RecipeNotFoundException;
import softuni.meal_plan.model.entity.Recipe;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.repository.*;
import softuni.meal_plan.service.RecipeService;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final Logger logger = Logger.getLogger(RecipeServiceImpl.class.getName());

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IngredientRepository ingredientRepository;
    private final PlannedMealRepository plannedMealRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository, IngredientRepository ingredientRepository, ModelMapper modelMapper, PlannedMealRepository plannedMealRepository, RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
        this.plannedMealRepository = plannedMealRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public RecipeServiceModel addRecipe(RecipeServiceModel recipeServiceModel) {
        Recipe recipe = this.modelMapper.map(recipeServiceModel, Recipe.class);
        return this.modelMapper.map(this.recipeRepository.saveAndFlush(recipe), RecipeServiceModel.class);
    }

    @Override
    public List<RecipeServiceModel> findAllRecipes() {
        return this.recipeRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RecipeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public RecipeServiceModel findRecipeById(String recipeId) {
        return this.recipeRepository.findById(recipeId)
                .map(r -> this.modelMapper.map(r, RecipeServiceModel.class))
                .orElse(null);
    }

    @Async
    @Transactional
    @Override
    public void deleteRecipe(String id) {
        logger.info("Execute method asynchronously. On thread: " + Thread.currentThread().getName());
        Recipe recipe = this.recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("Recipe with given id was not found!"));
        this.plannedMealRepository.deletePlannedMealsByRecipe_Id(id);
        this.recipeIngredientRepository.deleteRecipeIngredientsByRecipe_Id(id);
        this.recipeRepository.delete(recipe);
    }
}


