package com.ynov.todosapp.exceptions.user;

public class EmailAlreadyTaken extends RuntimeException {
  public EmailAlreadyTaken() {
    super("Email already in use");
  }
}
