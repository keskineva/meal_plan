package softuni.meal_plan.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

public class RecipeAddBindingModel {

    public static class Row {
        private String ingredient;
        private int amount;

        public Row() {
            this.amount = 1;
        }

        @Length(min = 3, message = "Enter at least 3 characters for the ingredient")
        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

        @Min(value = 1, message = "Amount must be positive")
        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }

    private byte[] image;
    private String name;
    private String instructions;
    private int portionsCount;
    private List<Row> ingredientsList;

    public RecipeAddBindingModel() {
        ingredientsList = new LinkedList<>();
        ingredientsList.add(new Row());
        portionsCount = 1;
    }

    @Length(min = 2, message = "Name length must be more than two characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 5, message = "Instructions length must be more than 5 characters")
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Min(value = 1, message = "Enter valid number of portions")
    public int getPortionsCount() {
        return portionsCount;
    }

    public void setPortionsCount(int portionsCount) {
        this.portionsCount = portionsCount;
    }

    @Size(min = 1, message = "Enter at least one ingredient")
    public List<Row> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Row> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
