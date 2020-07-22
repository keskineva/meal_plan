package softuni.meal_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.meal_plan.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
