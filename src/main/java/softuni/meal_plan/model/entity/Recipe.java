package softuni.meal_plan.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {
    private String name;
    private String instructions;
    private int portionsCount;
    private User author;

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
}
