package softuni.meal_plan.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {
    private String name;
    private String instructions;
    private int portionsCount;
    private User author;
    private List<Ingredient> ingredients;

    public Recipe() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "instructions", nullable = false)
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Column(name = "portions_count", nullable = false)
    public int getPortionsCount() {
        return portionsCount;
    }

    public void setPortionsCount(int portionsCount) {
        this.portionsCount = portionsCount;
    }

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    @ManyToMany(targetEntity = Ingredient.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(
                    name = "recipe_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ingredient_id",
                    referencedColumnName = "id"
            )
    )
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
