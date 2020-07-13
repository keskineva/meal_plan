package softuni.meal_plan.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.meal_plan.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByAndAuthority(String authority);
}
