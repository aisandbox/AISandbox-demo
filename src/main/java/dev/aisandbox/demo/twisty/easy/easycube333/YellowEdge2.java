package dev.aisandbox.demo.twisty.easy.easycube333;

import static dev.aisandbox.demo.twisty.easy.easycube333.YellowEdge1.EDGE_SWITCH;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YellowEdge2 implements SolverAlgorithm {

  @Override
  public boolean isValid(String state) {
    if (state.matches(".Y.YYY.Y..R.RRRRRR.G.GGGGGG...OOOOOO...BBBBBBWWWWWWWWW"))
      return false; // front & left edge already solved
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Solving yellow/red edge for state {}", state);
    EdgePosition p = Finder.findEdge(state, 'Y', 'R');
    log.info("Found at position {}", p.name());
    switch (p) {
      case EDGE_TOP_RIGHT:
        return "y2 " + EDGE_SWITCH + " y " + EDGE_SWITCH + " y";
      case EDGE_TOP_BACK:
        return "y " + EDGE_SWITCH + " y'";
      default:
        throw new SolverHaltException("Can't find yellow/red edge cube");
    }
  }
}
