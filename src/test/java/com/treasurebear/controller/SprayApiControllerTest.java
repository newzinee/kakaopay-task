package com.treasurebear.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treasurebear.code.ErrorCode;
import com.treasurebear.domain.ResponseDto;
import com.treasurebear.domain.SprayRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SprayApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testApiSpray() throws Exception {
        Integer sprayMoney = 1000;
        Integer sprayNumber = 3;
        String content = objectMapper.writeValueAsString(new SprayRequestDto(sprayMoney, sprayNumber));
        mockMvc.perform(post("/api/spray")
                        .header("X-USER-ID", 123)
                        .header("X-ROOM-ID", "room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetSplit_wrong_token() throws Exception {
        String result = objectMapper.writeValueAsString(new ResponseDto(ErrorCode.INVALID_REQUEST));
        mockMvc.perform(get("/api/get/abcd")
                            .header("X-USER-ID", 123)
                            .header("X-ROOM-ID", "room"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(result))
                .andDo(print());
    }

    @Test
    public void testGetSplit_right_token() throws Exception {
//        Integer sprayMoney = 1000;
//        Integer sprayNumber = 3;
//        String content = objectMapper.writeValueAsString(new SprayRequestDto(sprayMoney, sprayNumber));
//        String sprayResult = "";
//        mockMvc.perform(post("/api/spray")
//                .header("X-USER-ID", 123)
//                .header("X-ROOM-ID", "room")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content))
//                .andExpect(status().isOk())
//                .andDo(print());

        mockMvc.perform(get("/api/get/0xr")
                .header("X-USER-ID", 1234)
                .header("X-ROOM-ID", "room"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testList_fail() throws Exception {
        String result = objectMapper.writeValueAsString(new ResponseDto(ErrorCode.INVALID_REQUEST));

        mockMvc.perform(get("/api/list/0xr")
                .header("X-USER-ID", 1234)
                .header("X-ROOM-ID", "room"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(result))
                .andDo(print());
    }

    @Test
    public void testList_success() throws Exception {

        mockMvc.perform(get("/api/list/UrU")
                .header("X-USER-ID", 123)
                .header("X-ROOM-ID", "room"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}