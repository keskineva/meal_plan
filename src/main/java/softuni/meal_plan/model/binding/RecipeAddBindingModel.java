package softuni.meal_plan.model.binding;

import java.util.LinkedList;
import java.util.List;

public class RecipeAddBindingModel {

    public static class Row {
        private String ingredient;
        private int amount;

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

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
