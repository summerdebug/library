package com.example.library.service;

import com.example.library.dto.AuthorDto;
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

  public Author create(AuthorDto authorDto) {
    Author author = new Author(authorDto.firstName(), authorDto.lastName());
    return authorRepository.save(author);
  }

  public Author update(Long id, AuthorDto authorDto)
      throws AuthorNotFoundException {
    Author author = authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
    author.setFirstName(authorDto.firstName());
    author.setLastName(authorDto.lastName());
    return authorRepository.save(author);
  }
}
