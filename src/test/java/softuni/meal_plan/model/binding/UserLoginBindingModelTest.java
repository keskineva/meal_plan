package softuni.meal_plan.model.binding;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserLoginBindingModelTest {
    @Test
    public void test_Parameters() throws Exception {
        assertThat(UserLoginBindingModel.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
    }
}