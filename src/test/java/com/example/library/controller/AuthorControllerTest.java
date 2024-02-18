package com.example.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AuthorRepository authorRepository;

  @Test
  public void whenGetAllThenAuthorsReturned() throws Exception {
    authorRepository.deleteAll();
    List<Author> authors = List.of(
        new Author("Mark", "Twain"),
        new Author("Jack", "London")
    );
    authorRepository.saveAll(authors);

    mockMvc.perform(get("/authors")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", Matchers.hasSize(2)))
        .andExpect(jsonPath("$[0].firstName", is("Mark")));
  }

  @Test
  public void whenGetByIdThenAuthorReturned() throws Exception {
    String firstName = "George";
    String lastName = "Orwell";
    Author author = authorRepository.save(new Author(firstName, lastName));

    mockMvc.perform(get("/authors/" + author.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.firstName", is(firstName)))
        .andExpect(jsonPath("$.lastName", is(lastName)));
  }

  @Test
  public void givenIdNotExistWhenGetAuthorThenNotFound() throws Exception {
    mockMvc.perform(get("/authors/" + Long.MAX_VALUE))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorMsg", is("Author not found.")));
  }

}