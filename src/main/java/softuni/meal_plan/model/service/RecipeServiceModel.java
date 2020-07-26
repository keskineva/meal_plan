package softuni.meal_plan.model.service;

import org.hibernate.validator.constraints.Length;
import softuni.meal_plan.model.entity.User;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

public class RecipeServiceModel extends BaseServiceModel {
    private String name;
    private byte[] image;
    private String instructions;
    private int portionsCount;
    private User author;
    private List<IngredientServiceModel> ingredients;

    @Length(min = 2, message = "Recipe name length must be more than two characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Please link a picture for this recipe!")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Length(min = 5, message = "Recipe instructions length must be more than 5 characters")
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @DecimalMin(value = "1", message = "Please enter a valid number of portions!")
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

    @NotNull(message = "Please enter at least one ingredient!")
    public List<IngredientServiceModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientServiceModel> ingredients) {
        this.ingredients = ingredients;
    }
}
