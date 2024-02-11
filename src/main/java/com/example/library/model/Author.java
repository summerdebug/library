package com.example.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "author")
public class Author {

  @Id
  @GeneratedValue
  private Long id;

  private String firstName;

  private String lastName;

}
