package softuni.meal_plan.web;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void showRecipe() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/recipes/show")
                        .with(user("ava").password("1234").roles("USER"))
                .param("id", "1d383a6a-7d04-4c6a-9f68-e76629cdc037"))
                .andExpect(status().is(200))
                .andExpect(view().name("recipe/show-recipe"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void add() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/recipes/add")
                        .with(user("ava").password("1234").roles("USER"))
                )
                .andExpect(status().is(200))
                .andExpect(view().name("recipe/add-recipe"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void addConfirm() {
    }

    @Test
    void addRow() {
    }

    @Test
    void removeRow() {
    }

    @Test
    void deleteRecipe() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/recipes/delete/1d383a6a-7d04-4c6a-9f68-e76629cdc037")
                        .with(user("ava").password("1234").roles("ADMIN"))
                )
                .andExpect(status().is(200))
                .andExpect(view().name("recipe/delete-recipe"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteRecipeConfirm() {
    }

    @Test
    void showAllRecipes() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/recipes/all")
                        .with(user("ava").password("1234").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(view().name("recipe/all-recipes"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void addToPlan() throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime( new Date());
        c.add(Calendar.DATE, 1); // get tomorrows date
        // prepare date formatter
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/recipes/all")
                    .with(user("ava").password("1234").roles("USER"))
                    .param("addToPlan", "")
                    .param("portions_count", "2")
                    .param("recipe_id", "1d383a6a-7d04-4c6a-9f68-e76629cdc037")
                    .param("planned_date", sdf.format(c.getTime())) // get formatted date
                    .param("meal_type", "LUNCH")
                )
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }
}