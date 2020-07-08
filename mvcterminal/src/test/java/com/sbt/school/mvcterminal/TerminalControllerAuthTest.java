package com.sbt.school.mvcterminal;

import com.sbt.school.mvcterminal.controllers.TerminalController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TerminalControllerAuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TerminalController controller;

    @Test
    public void whenRootParamsNullThenNoMessages() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("class=\"errorMsg\""))))
                .andExpect(content().string(not(containsString("class=\"successMsg\""))));

    }

    @Test
    public void whenRootParamErrorNotNullThenErrorMsg() throws Exception {
        mockMvc.perform(get("/").param("error_msg", "Test error msg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("class=\"errorMsg\"")))
                .andExpect(content().string(not(containsString("class=\"successMsg\""))));
    }

    @Test
    public void whenRootParamSuccessNotNullThenSuccessMsg() throws Exception {
        mockMvc.perform(get("/").param("success_msg", "Test success msg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("class=\"errorMsg\""))))
                .andExpect(content().string(containsString("class=\"successMsg\"")));
    }

    @Test
    public void whenAuthPinCorrectThenMainMenu() throws Exception {
        mockMvc.perform(post("/auth").param("pin", "1542"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", "/menu"));
    }

    @Test
    public void whenAuthPinIncorrectButInFormatThenErrorMsg() throws Exception {
        mockMvc.perform(post("/auth").param("pin", "1111"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("error_msg")));
    }

    @Test
    public void whenAuthPinNotInFormatThenErrorMsg() throws Exception {
        mockMvc.perform(post("/auth").param("pin", "af215f"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }



}
