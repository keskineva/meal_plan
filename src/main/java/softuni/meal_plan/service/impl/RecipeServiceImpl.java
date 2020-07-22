package softuni.meal_plan.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import softuni.meal_plan.error.RecipeNotFoundException;
import softuni.meal_plan.model.entity.Recipe;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.repository.IngredientRepository;
import softuni.meal_plan.repository.RecipeRepository;
import softuni.meal_plan.repository.UserRepository;
import softuni.meal_plan.service.RecipeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository, IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RecipeServiceModel addRecipe(RecipeServiceModel recipeServiceModel) {
        Recipe recipe = this.modelMapper.map(recipeServiceModel, Recipe.class);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //recipe.setAuthor((User)auth.getPrincipal()); //TODO
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

    @Override
    public void deleteRecipe(String id) {
        Recipe recipe = this.recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("Recipe with given id was not found!"));

        this.recipeRepository.delete(recipe);
    }
}


