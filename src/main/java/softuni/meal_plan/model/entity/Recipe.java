package softuni.meal_plan.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {
    private String name;
    private byte[] image;
    private String instructions;
    private int portionsCount;
    private User author;
    private List<RecipeIngredient> ingredients;

    public Recipe() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Column(name = "instructions", nullable = false, columnDefinition="TEXT")
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


    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
