package dev.aisandbox.demo.twisty.easy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.RESET_CONTENT)
public class SolverResetException extends SolverException {

  public SolverResetException(String message) {
    super(message);
  }
}
