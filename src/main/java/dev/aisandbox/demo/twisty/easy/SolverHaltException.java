package dev.aisandbox.demo.twisty.easy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class SolverHaltException extends SolverException {

  public SolverHaltException(String message) {
    super(message);
  }
}
