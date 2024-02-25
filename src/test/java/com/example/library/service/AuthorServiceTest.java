package com.example.library.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.example.library.dto.AuthorDto;
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
  private AuthorRepository authorRepositoryMock;

  @Mock
  private Author authorMock;

  private AuthorService authorService;

  @BeforeEach
  public void setup() {
    authorService = new AuthorService(authorRepositoryMock);
  }

  @Test
  public void whenGetAllThenSuccessful() {
    Iterable<Author> authorsExpected = new ArrayList<>();
    when(authorRepositoryMock.findAll()).thenReturn(authorsExpected);

    Iterable<Author> authorsActual = authorService.getAll();

    assertSame(authorsExpected, authorsActual);
    verify(authorRepositoryMock).findAll();
  }

  @Test
  public void whenGetAuthorThenSuccessful() throws Exception {
    Long authorId = 1L;
    Author authorExpected = new Author("George", "Orwell");
    when(authorRepositoryMock.findById(authorId)).thenReturn(Optional.of(authorExpected));

    Author authorActual = authorService.get(authorId);

    assertSame(authorExpected, authorActual);
    verify(authorRepositoryMock).findById(authorId);
  }

  @Test
  public void givenIdNotExistWhenGetThenException() {
    long authorId = 1L;
    String errorMsg = "Author not found.";
    when(authorRepositoryMock.findById(authorId)).thenReturn(Optional.empty());

    assertThrows(AuthorNotFoundException.class, () -> authorService.get(authorId), errorMsg);
    verify(authorRepositoryMock).findById(authorId);
  }

  @Test
  public void whenCreateThenSuccessful() {
    Author authorExpected = new Author("George", "Orwell");
    when(authorRepositoryMock.save(any())).thenReturn(authorExpected);
    AuthorDto authorDto = new AuthorDto("George", "Orwell");

    Author authorActual = authorService.create(authorDto);

    assertSame(authorExpected, authorActual);
    verify(authorRepositoryMock).save(any(Author.class));
  }

  @Test
  public void whenUpdateThenSuccessful() throws Exception {
    when(authorRepositoryMock.findById(1L)).thenReturn(Optional.of(authorMock));
    when(authorRepositoryMock.save(authorMock)).thenReturn(authorMock);
    AuthorDto authorDto = new AuthorDto("George", "Orwell");

    Author authorActual = authorService.update(1L, authorDto);

    assertSame(authorMock, authorActual);
    verify(authorMock).setFirstName("George");
    verify(authorMock).setLastName("Orwell");
  }

  @Test
  public void givenAuthorNotExistWhenUpdateThenNotFound() {
    when(authorRepositoryMock.findById(1L)).thenReturn(Optional.empty());
    AuthorDto authorDto = new AuthorDto("George", "Orwell");

    assertThrows(AuthorNotFoundException.class, () -> authorService.update(1L, authorDto));
  }
}