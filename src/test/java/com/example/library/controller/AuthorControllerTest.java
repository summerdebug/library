package com.example.library.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.library.dto.AuthorDto;
import com.example.library.exception.AuthorNotFoundException;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.hamcrest.CoreMatchers;

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

  @Test
  public void whenCreateThenAuthorCreated() throws Exception {
    AuthorDto authorDto = new AuthorDto("Mark", "Twain");
    ObjectMapper mapper = new ObjectMapper();
    String authorJson = mapper.writeValueAsString(authorDto);

    mockMvc.perform(post("/authors/create")
            .content(authorJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()));
  }

  @Test
  public void whenUpdateThenAuthorUpdated() throws Exception {
    Author authorInitial = authorRepository.save(new Author("George", "Orwell"));
    AuthorDto authorNew = new AuthorDto("Mark", "Twain");
    ObjectMapper mapper = new ObjectMapper();
    String authorNewJson = mapper.writeValueAsString(authorNew);

    mockMvc.perform(put("/authors/update/" + authorInitial.getId())
            .content(authorNewJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    Author authorActual = authorRepository.findById(authorInitial.getId()).orElseThrow(
        AuthorNotFoundException::new);
    assertEquals(authorActual.getFirstName(), authorNew.firstName());
    assertEquals(authorActual.getLastName(), authorNew.lastName());
  }

  @Test
  public void givenAuthorNotExistWhenUpdateThenNotFound() throws Exception {
    AuthorDto author = new AuthorDto("Mark", "Twain");
    ObjectMapper mapper = new ObjectMapper();
    String authorJson = mapper.writeValueAsString(author);

    mockMvc.perform(put("/authors/update/" + Long.MAX_VALUE)
            .content(authorJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errorMsg", is("Author not found.")));
  }

}