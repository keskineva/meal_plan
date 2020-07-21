package softuni.meal_plan.model.service;

public class RecipeIngredientServiceModel {

    private RecipeServiceModel recipe;
    private IngredientServiceModel ingredient;
    private Integer amount;

    public RecipeIngredientServiceModel() {
    }

    public RecipeServiceModel getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeServiceModel recipe) {
        this.recipe = recipe;
    }

    public IngredientServiceModel getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientServiceModel ingredient) {
        this.ingredient = ingredient;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
