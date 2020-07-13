package softuni.meal_plan.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {
    private int ingredientAmount;
    private Ingredient ingredient;
    private List<Ingredient> ingredients;

    public Cart() {
    }

    @Column(name = "ingredient_amount", nullable = false)
    public int getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(int ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    @ManyToOne
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    @ManyToMany(targetEntity = Ingredient.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "cart_ingredients",
            joinColumns = @JoinColumn(
                    name = "cart_id",
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
