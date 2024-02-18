package com.example.library.service;

import com.example.library.dto.AuthorDto;
import com.example.library.exception.AuthorNotFoundException;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;

  public Iterable<Author> getAll() {
    return authorRepository.findAll();
  }

  public Author get(Long id) throws AuthorNotFoundException {
    return authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
  }

  public Author save(@RequestBody AuthorDto authorDto) {
    Author author = new Author(authorDto.firstName(), authorDto.lastName());
    return authorRepository.save(author);
  }
}
