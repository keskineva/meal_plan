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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrder() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/create")
                        .with(user("ava").password("1234").roles("USER")))
                .andExpect(status().is(200))
                .andExpect(view().name("order/create-order"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void createOrderConfirm() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/orders/create")
                        .with(user("ava").password("1234").roles("USER"))
                        .param("order", ""))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("totalIngredientsAndAmountsMap"))
                .andExpect(view().name("order/create-order"))
                .andDo(MockMvcResultHandlers.print());
    }
}