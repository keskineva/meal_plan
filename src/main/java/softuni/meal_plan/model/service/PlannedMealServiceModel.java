package softuni.meal_plan.model.service;

import java.util.Date;

public class PlannedMealServiceModel extends BaseServiceModel {


    private int plannedPortionsCount;
    private Date plannedDateTime;
    private UserServiceModel user;
    private RecipeServiceModel recipe;

    public PlannedMealServiceModel() {
    }

    public PlannedMealServiceModel(int plannedPortionsCount, Date plannedDateTime, UserServiceModel user, RecipeServiceModel recipe) {
        this.plannedPortionsCount = plannedPortionsCount;
        this.plannedDateTime = plannedDateTime;
        this.user = user;
        this.recipe = recipe;
    }

    public int getPlannedPortionsCount() {
        return plannedPortionsCount;
    }

    public void setPlannedPortionsCount(int plannedPortionsCount) {
        this.plannedPortionsCount = plannedPortionsCount;
    }

    public Date getPlannedDateTime() {
        return plannedDateTime;
    }

    public void setPlannedDateTime(Date plannedDateTime) {
        this.plannedDateTime = plannedDateTime;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public RecipeServiceModel getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeServiceModel recipe) {
        this.recipe = recipe;
    }
}
