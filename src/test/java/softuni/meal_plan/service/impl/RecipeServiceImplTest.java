package softuni.meal_plan.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import softuni.meal_plan.model.entity.Recipe;
import softuni.meal_plan.model.entity.User;
import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.repository.IngredientRepository;
import softuni.meal_plan.repository.RecipeRepository;
import softuni.meal_plan.repository.UserRepository;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

class RecipeServiceImplTest {

    private RecipeRepository mockRecipeRepository = Mockito.mock(RecipeRepository.class);
    private UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
    private IngredientRepository mockIngredientRepository = Mockito.mock(IngredientRepository.class);
    private ModelMapper modelMapper = new ModelMapper();

    @Test
    void addRecipe() {
        RecipeServiceImpl recipeService = new RecipeServiceImpl(mockRecipeRepository,mockUserRepository,mockIngredientRepository,modelMapper);

        RecipeServiceModel recipeServiceModel = new RecipeServiceModel();
        recipeServiceModel.setName("Test Recipe");
        recipeServiceModel.setInstructions("Test Instructions");
        recipeServiceModel.setPortionsCount(0);
        recipeServiceModel.setImage(new byte[]{});
        recipeServiceModel.setIngredients(new ArrayList<IngredientServiceModel>());
        recipeServiceModel.setId("testID");
        recipeServiceModel.setAuthor(new User());

        recipeService.addRecipe(recipeServiceModel);
        Mockito.verify(mockRecipeRepository, Mockito.times(1)).saveAndFlush(any(Recipe.class));
    }

    @Test
    void findAllRecipes() {
    }

    @Test
    void findRecipeById() {
    }

    @Test
    void deleteRecipe() {
    }
}