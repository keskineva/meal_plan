package softuni.meal_plan.model.entity;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

class IngredientPriceTest {
    @Test
    public void test_Parameters() throws Exception {
        assertThat(IngredientPrice.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
    }
}