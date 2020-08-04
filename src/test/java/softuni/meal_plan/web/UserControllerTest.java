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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginGet() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/login"))
                .andExpect(status().is(200))
                .andExpect(view().name("user/login"))
                .andExpect(MockMvcResultMatchers.model().attribute("notFound", false))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testRegisterGet() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/register"))
                .andExpect(status().is(200))
                .andExpect(view().name("user/register"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testProfileGet() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/profile")
                        .with(user("ava").password("1234").roles("ADMIN")))
                .andExpect(view().name("user/profile"))
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAllUsersGet() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/all")
                        .with(user("ddd").password("ddd").roles("ADMIN")))
                .andExpect(view().name("user/all-users"))
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEditProfileGet() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/edit")
                        .with(user("ddd").password("ddd").roles("USERS")))
                .andExpect(view().name("user/edit-profile"))
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }
}