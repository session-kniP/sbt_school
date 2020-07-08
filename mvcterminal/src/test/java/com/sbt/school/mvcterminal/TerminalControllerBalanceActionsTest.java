package com.sbt.school.mvcterminal;

import com.sbt.school.mvcterminal.controllers.TerminalController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TerminalControllerBalanceActionsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TerminalController controller;

    @BeforeEach
    public void doBefore() throws Exception {
        mockMvc.perform(post("/auth").param("pin", "1542"));
    }

    @Test
    public void whenTakeMoneyParamIsCorrectThenSuccessMsgAndRedirectToMenu() throws Exception {
        mockMvc.perform(post("/takeMoney").param("amount", "100"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/menu?success_msg")));
    }

    @Test
    public void whenTakeMoneyParamIsNullThenErrorMsgAndRedirectToWithdraw() throws Exception {
        mockMvc.perform(post("/takeMoney"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenTakeMoneyParamIsIncorrectThenErrorMessageAndRedirectToWithdraw() throws Exception {
        mockMvc.perform(post("/takeMoney").param("amount", "112"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/withdraw?error_msg")));
    }

    @Test
    public void whenPutMoneyParamIsCorrectThenSuccessMsgAndRedirectToMenu() throws Exception {
        mockMvc.perform(post("/putMoney").param("amount", "100"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/menu?success_msg")));
    }

    @Test
    public void whenPutMoneyParamIsNullThenErrorMsgAndRedirectToWithdraw() throws Exception {
        mockMvc.perform(post("/putMoney"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void whenPutMoneyParamIsIncorrectThenErrorMessageAndRedirectToReplenish() throws Exception {
        mockMvc.perform(post("/putMoney").param("amount", "112"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/replenish?error_msg")));
    }
}
