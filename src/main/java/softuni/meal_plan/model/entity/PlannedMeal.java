package softuni.meal_plan.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "planned_meals")
public class PlannedMeal extends BaseEntity {
    private int plannedPortionsCount;
    private Date plannedDateTime;
    private User user;
    private Recipe recipe;

    public PlannedMeal() {
    }

    @Column(name = "portions", nullable = false)
    public int getPlannedPortionsCount() {
        return plannedPortionsCount;
    }

    public void setPlannedPortionsCount(int plannedPortionsCount) {
        this.plannedPortionsCount = plannedPortionsCount;
    }

    @Column(name = "planned_date", nullable = false)
    public Date getPlannedDateTime() {
        return plannedDateTime;
    }

    public void setPlannedDateTime(Date plannedDateTime) {
        this.plannedDateTime = plannedDateTime;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
