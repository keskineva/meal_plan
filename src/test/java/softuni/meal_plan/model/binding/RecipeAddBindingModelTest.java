package softuni.meal_plan.model.binding;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

class RecipeAddBindingModelTest {
    @Test
    public void testRecipeAddBindingModel_Parameters() throws Exception {
        assertThat(RecipeAddBindingModel.class, allOf(hasValidBeanConstructor(), hasValidGettersAndSetters()));
    }
}