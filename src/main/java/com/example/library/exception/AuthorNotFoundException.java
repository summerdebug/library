package com.example.library.exception;

public class AuthorNotFoundException extends Exception {

  public AuthorNotFoundException() {
    super("Author not found.");
  }

}
