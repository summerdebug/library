package com.example.library.controller;

import com.example.library.service.AuthorService;
import com.example.library.exception.AuthorNotFoundException;
import com.example.library.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorService authorService;

  @GetMapping
  public Iterable<Author> getAll() {
    return authorService.getAll();
  }

  @GetMapping(path = "/{id}")
  public Author get(@PathVariable(name = "id") Long id) throws AuthorNotFoundException {
    return authorService.get(id);
  }

}
