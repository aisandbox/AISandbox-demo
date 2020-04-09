package dev.aisandbox.demo.twisty.easy.easycube333;

import static dev.aisandbox.demo.twisty.easy.easycube333.YellowEdge1.EDGE_SWITCH;

import dev.aisandbox.demo.twisty.easy.SolverException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YellowEdge3 implements SolverAlgorithm {

  @Override
  public boolean isValid(String state) {
    if (state.matches(".Y.YYY.Y..R.RRRRRR.G.GGGGGG.O.OOOOOO.B.BBBBBBWWWWWWWWW"))
      return false; // front & left edge already solved
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Solving last yellow edges yellow/orange and yellow/blue {}", state);
    return "y2 " + EDGE_SWITCH + " y2";
  }
}
