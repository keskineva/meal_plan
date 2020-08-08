package softuni.meal_plan.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import softuni.meal_plan.model.entity.Recipe;
import softuni.meal_plan.model.service.RecipeServiceModel;
import softuni.meal_plan.repository.*;
import softuni.meal_plan.service.RecipeService;

class RecipeServiceImplTest {

    public static final String TEST_ID = "TestId";
    private RecipeRepository recipeRepositoryMock;
    private UserRepository userRepositoryMock;
    private IngredientRepository ingredientRepositoryMock;
    private PlannedMealRepository plannedMealRepositoryMock;
    private RecipeIngredientRepository recipeIngredientRepositoryMock;

    @BeforeEach
    void init() {
        this.ingredientRepositoryMock = Mockito.mock(IngredientRepository.class);
        this.plannedMealRepositoryMock = Mockito.mock(PlannedMealRepository.class);
        this.recipeIngredientRepositoryMock = Mockito.mock(RecipeIngredientRepository.class);
        this.userRepositoryMock = Mockito.mock(UserRepository.class);
        this.recipeRepositoryMock = Mockito.mock(RecipeRepository.class);
    }


    @Test
    void addRecipe() {
        Mockito.when(recipeRepositoryMock.saveAndFlush(Mockito.any(Recipe.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        RecipeService recipeService = new RecipeServiceImpl(recipeRepositoryMock, userRepositoryMock, ingredientRepositoryMock, new ModelMapper(), plannedMealRepositoryMock, recipeIngredientRepositoryMock);
        // act
        RecipeServiceModel model = new RecipeServiceModel();
        recipeService.addRecipe(model);

        // assert
        Mockito.verify(recipeRepositoryMock, Mockito.times(1)).saveAndFlush(Mockito.any(Recipe.class));
    }

    @Test
    void deleteRecipe() {
        Recipe recipe = new Recipe();
        Mockito.when(recipeRepositoryMock.findById(TEST_ID)).thenReturn(java.util.Optional.of(recipe));
        RecipeService recipeService = new RecipeServiceImpl(recipeRepositoryMock, userRepositoryMock, ingredientRepositoryMock, new ModelMapper(), plannedMealRepositoryMock, recipeIngredientRepositoryMock);

        // act
        recipeService.deleteRecipe(TEST_ID);

        // assert
        Mockito.verify(plannedMealRepositoryMock, Mockito.times(1)).deletePlannedMealsByRecipe_Id(TEST_ID);
        Mockito.verify(recipeIngredientRepositoryMock, Mockito.times(1)).deleteRecipeIngredientsByRecipe_Id(TEST_ID);
        Mockito.verify(recipeRepositoryMock, Mockito.times(1)).delete(recipe);
    }
}