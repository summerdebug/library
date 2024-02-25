package com.example.library.controller;

import com.example.library.dto.AuthorDto;
import com.example.library.exception.AuthorNotFoundException;
import com.example.library.model.Author;
import com.example.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @GetMapping("/{id}")
  public Author get(@PathVariable("id") Long id) throws AuthorNotFoundException {
    return authorService.get(id);
  }

  @PostMapping("/create")
  public Author create(@RequestBody AuthorDto authorDto) {
    return authorService.create(authorDto);
  }

  @PutMapping("/update/{id}")
  public Author update(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto)
      throws AuthorNotFoundException {
    return authorService.update(id, authorDto);
  }

}
