package softuni.meal_plan.model.service;

import softuni.meal_plan.model.entity.Ingredient;
import softuni.meal_plan.model.entity.User;

import java.util.List;

public class RecipeServiceModel extends BaseServiceModel {
    private String name;
    private byte[] image;
    private String instructions;
    private int portionsCount;
    private User author;
    private List<Ingredient> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
