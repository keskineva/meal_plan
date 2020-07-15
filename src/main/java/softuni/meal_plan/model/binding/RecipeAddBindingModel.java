package softuni.meal_plan.model.binding;

import softuni.meal_plan.model.entity.Ingredient;

import java.util.List;

public class RecipeAddBindingModel {
    private String name;
    private String instructions;
    private int portionsCount;
    private List<Ingredient> ingredients;

    public RecipeAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getPortionsCount() {
        return portionsCount;
    }

    public void setPortionsCount(int portionsCount) {
        this.portionsCount = portionsCount;
    }


    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
