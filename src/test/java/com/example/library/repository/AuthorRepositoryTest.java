package com.example.library.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.model.Author;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.Matchers;

@DataJpaTest
class AuthorRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private AuthorRepository authorRepository;

  @Test
  public void whenFindAllThenAuthorsReturned() {
    authorRepository.deleteAll();
    testEntityManager.persist(new Author("Mark", "Twain"));
    testEntityManager.persist(new Author("Jack", "London"));

    Iterable<Author> authorsIterable = authorRepository.findAll();

    List<Author> authorsList = new ArrayList<>();
    authorsIterable.forEach(authorsList::add);
    assertEquals(2, authorsList.size());
    assertEquals("Mark", authorsList.get(0).getFirstName());
  }

  @Test
  public void whenFindByIdThenAuthorReturned() {
    Author authorExpected = testEntityManager.persist(new Author("Mark", "Twain"));

    Optional<Author> authorActual = authorRepository.findById(authorExpected.getId());

    assertTrue(authorActual.isPresent());
    assertEquals(authorExpected.getFirstName(), authorActual.get().getFirstName());
  }

  @Test
  public void givenIdNotExistWhenFindByIdThenEmpty() {
    Optional<Author> authorActual = authorRepository.findById(Long.MAX_VALUE);
    assertFalse(authorActual.isPresent());
  }

  @Test
  public void whenSaveThenAuthorSaved() {
    Author author = new Author("Mark", "Twain");

    Author authorSaved = authorRepository.save(author);

    assertNotNull(authorSaved.getId());
  }
}