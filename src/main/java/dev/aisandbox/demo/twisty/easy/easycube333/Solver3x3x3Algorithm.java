package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;

public interface Solver3x3x3Algorithm {
  public boolean isValid(String state);
  public String getMoves(String state) throws SolverException;

}
