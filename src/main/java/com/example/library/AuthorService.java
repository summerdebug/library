package com.example.library;

import com.example.library.exception.AuthorNotFoundException;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
