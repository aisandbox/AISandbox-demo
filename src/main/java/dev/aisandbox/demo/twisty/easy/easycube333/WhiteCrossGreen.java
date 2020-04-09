package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhiteCrossGreen implements SolverAlgorithm {
  @Override
  public boolean isValid(String state) {
    // If we've already solved the white side and turned the cube over - ignore
    if (state.matches("....Y..........RRR......GGG......OOO......BBBWWWWWWWWW")) return false;
    // if we've already got the white cross FIRST STEP
    if (state.matches("....W..W...........G.............................Y....")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move green/white edge to top {}", state);
    // find the green/white edge
    EdgePosition p = Finder.findEdge(state, 'W', 'G');
    log.info("Found piece in position {}",p.name());
    switch (p) {
        // put pieces on the top row to the bottom
      case EDGE_TOP_RIGHT:
      case EDGE_RIGHT_TOP:
        return "R2";
      case EDGE_TOP_FRONT: // should never happen
      case EDGE_FRONT_TOP:
        return "F2";
      case EDGE_TOP_LEFT:
      case EDGE_LEFT_TOP:
        return "L2";
      case EDGE_TOP_BACK:
      case EDGE_BACK_TOP:
        return "B2";
        // front rotations
      case EDGE_RIGHT_FRONT:
        return "F'";
      case EDGE_BOTTOM_FRONT:
        return "F2";
      case EDGE_LEFT_FRONT:
        return "F";
        // front rotations (wrong side)
      case EDGE_FRONT_LEFT:
        return "Dw F'";
      case EDGE_FRONT_RIGHT:
        return "Dw' F";
      case EDGE_FRONT_BOTTOM:
        return "D' Rw' D Rw";
        // remaining middle rows to bottom
      case EDGE_RIGHT_BACK:
      case EDGE_BACK_RIGHT:
        return "B'";
      case EDGE_LEFT_BACK:
      case EDGE_BACK_LEFT:
        return "B";
        // bottom to front
      case EDGE_BACK_BOTTOM:
      case EDGE_BOTTOM_BACK:
        return "D2";
      case EDGE_RIGHT_BOTTOM:
      case EDGE_BOTTOM_RIGHT:
        return "D'";
      case EDGE_BOTTOM_LEFT:
      case EDGE_LEFT_BOTTOM:
        return "D";
    }
    throw new SolverHaltException("Can't find green/white edge cube");
  }

}
