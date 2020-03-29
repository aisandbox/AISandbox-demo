package dev.aisandbox.demo.twisty.easy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class SolverException extends Exception {

  public SolverException(String message) {
    super(message);
  }
}
