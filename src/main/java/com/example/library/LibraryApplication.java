package com.example.library;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

  public static void main(String[] args) {
    SpringApplication.run(LibraryApplication.class, args);
  }

  @Bean
  public CommandLineRunner initDatabase(AuthorRepository authorRepository) {
    return args -> {
      List<Author> authors = List.of(
          new Author("Mark", "Twain"),
          new Author("Jack", "London")
      );
      authorRepository.saveAll(authors);
    };
  }

}
