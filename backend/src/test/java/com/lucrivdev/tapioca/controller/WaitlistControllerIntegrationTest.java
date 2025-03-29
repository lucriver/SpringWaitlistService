package com.lucrivdev.tapioca.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class WaitlistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldAddEmailToWaitlist() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldAddEmailWithAliasToWaitlist() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test+alias@example.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldThrowEmailAlreadyAdded() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@example.com\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowInvalidEmail() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"not-an-email\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowEmptyEmail() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowNullEmail() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": null}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowMissingEmailField() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"example_field\": \"\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowMalformedJson() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{{\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowInvalidContentType() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("example"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldThrowEmailWithUnicodeChars() throws Exception {
        mockMvc.perform(post("/waitlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"用@example.com\"}"))
                .andExpect(status().is4xxClientError());
    }


}
