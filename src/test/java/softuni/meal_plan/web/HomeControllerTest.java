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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is(200))
                .andExpect(view().name("index"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void home() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/home")
                        .with(user("ava").password("1234").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(view().name("home"))
                .andDo(MockMvcResultHandlers.print());
    }
}