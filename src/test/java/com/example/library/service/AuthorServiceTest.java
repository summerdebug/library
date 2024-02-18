package com.example.library.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.library.exception.AuthorNotFoundException;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

  @Mock
  private AuthorRepository authorRepository;

  private AuthorService authorService;

  @BeforeEach
  public void setup() {
    authorService = new AuthorService(authorRepository);
  }

  @Test
  public void whenGetAllThenSuccessful() {
    Iterable<Author> authorsExpected = new ArrayList<>();
    when(authorRepository.findAll()).thenReturn(authorsExpected);

    Iterable<Author> authorsActual = authorService.getAll();

    assertSame(authorsExpected, authorsActual);
    verify(authorRepository).findAll();
  }

  @Test
  public void whenGetAuthorThenSuccessful() throws Exception {
    Long authorId = 1L;
    Author authorExpected = new Author("George", "Orwell");
    when(authorRepository.findById(authorId)).thenReturn(Optional.of(authorExpected));

    Author authorActual = authorService.get(authorId);

    assertSame(authorExpected, authorActual);
    verify(authorRepository).findById(authorId);
  }

  @Test
  public void givenIdNotExistWhenGetThenException() {
    long authorId = 1L;
    String errorMsg = "Author not found.";
    when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

    assertThrows(AuthorNotFoundException.class, () -> authorService.get(authorId), errorMsg);
    verify(authorRepository).findById(authorId);
  }

}