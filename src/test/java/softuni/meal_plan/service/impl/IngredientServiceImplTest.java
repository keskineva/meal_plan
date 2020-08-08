package softuni.meal_plan.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import softuni.meal_plan.model.entity.Ingredient;
import softuni.meal_plan.model.entity.RecipeIngredient;
import softuni.meal_plan.model.service.IngredientServiceModel;
import softuni.meal_plan.model.service.RecipeIngredientServiceModel;
import softuni.meal_plan.repository.IngredientRepository;
import softuni.meal_plan.repository.RecipeIngredientRepository;
import softuni.meal_plan.service.IngredientService;

class IngredientServiceImplTest {

    public static final String TEST_INGREDIENT_NAME = "testIngredient";
    public static final String TEST_INGREDIENT_ID = "testIngredientId";
    private IngredientRepository ingredientRepositoryMock;
    private Ingredient testIngredient;
    private RecipeIngredientRepository recipeIngredientRepositoryMock;

    @BeforeEach
    void init() {

        this.testIngredient = new Ingredient();
        this.testIngredient.setName(TEST_INGREDIENT_NAME);
        this.testIngredient.setId(TEST_INGREDIENT_ID);

        this.ingredientRepositoryMock = Mockito.mock(IngredientRepository.class);
        this.recipeIngredientRepositoryMock = Mockito.mock(RecipeIngredientRepository.class);
    }


    @Test
    void saveIngredient_Existing() {
        Mockito.when(this.ingredientRepositoryMock.findByName(TEST_INGREDIENT_NAME)).thenReturn(this.testIngredient);

        IngredientService ingredientService = new IngredientServiceImpl(ingredientRepositoryMock, new ModelMapper(), recipeIngredientRepositoryMock);

        IngredientServiceModel actual = ingredientService.saveIngredient(this.testIngredient.getName());

        Assert.assertEquals("Mismatch...", testIngredient.getId(), actual.getId());
        Assert.assertEquals("Mismatch...", testIngredient.getName(), actual.getName());
    }

    @Test
    void saveIngredient_New() {
        Mockito.when(this.ingredientRepositoryMock.findByName(TEST_INGREDIENT_NAME)).thenReturn(null);
        Mockito.when(ingredientRepositoryMock.saveAndFlush(Mockito.any(Ingredient.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        IngredientService ingredientService = new IngredientServiceImpl(ingredientRepositoryMock, new ModelMapper(), recipeIngredientRepositoryMock);

        IngredientServiceModel actual = ingredientService.saveIngredient(this.testIngredient.getName());

        Assert.assertEquals("Mismatch...", testIngredient.getName(), actual.getName());
    }

    @Test
    void saveRecipeIngredient() {
        Mockito.when(recipeIngredientRepositoryMock.saveAndFlush(Mockito.any(RecipeIngredient.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        IngredientService ingredientService = new IngredientServiceImpl(ingredientRepositoryMock, new ModelMapper(), recipeIngredientRepositoryMock);
        RecipeIngredientServiceModel model = new RecipeIngredientServiceModel();
        model.setAmount(1);
        // act
        RecipeIngredientServiceModel actual = ingredientService.saveRecipeIngredient(model);

        // assert
        Mockito.verify(recipeIngredientRepositoryMock, Mockito.times(1)).saveAndFlush(Mockito.any(RecipeIngredient.class));
    }

    @Test
    void findIngredientById() {
        Mockito.when(this.ingredientRepositoryMock.findById(testIngredient.getId()))
                .thenReturn(java.util.Optional.ofNullable(this.testIngredient));
        IngredientService ingredientService = new IngredientServiceImpl(ingredientRepositoryMock, new ModelMapper(), recipeIngredientRepositoryMock);

        IngredientServiceModel actual = ingredientService.findIngredientById(this.testIngredient.getId());

        Assert.assertEquals("Mismatch...", testIngredient.getId(), actual.getId());

    }
}