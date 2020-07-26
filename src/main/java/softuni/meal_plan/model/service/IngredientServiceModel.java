package softuni.meal_plan.model.service;

import org.hibernate.validator.constraints.Length;

public class IngredientServiceModel extends BaseServiceModel {
    private String name;

    public IngredientServiceModel() {
    }

    public IngredientServiceModel(String name) {
        this.name = name;
    }
    @Length(min = 2, message = "Ingredient name length must be more than two characters")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
