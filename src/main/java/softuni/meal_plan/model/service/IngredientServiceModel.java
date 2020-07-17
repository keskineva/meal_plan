package softuni.meal_plan.model.service;

public class IngredientServiceModel extends BaseServiceModel {
    private String name;

    public IngredientServiceModel() {
    }

    public IngredientServiceModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
