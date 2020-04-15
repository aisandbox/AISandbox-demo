package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhiteCrossRed implements Solver3x3x3Algorithm {
  @Override
  public boolean isValid(String state) {
    // If we've already solved the white side and turned the cube over - ignore
    if (state.matches("....Y..........RRR......GGG......OOO......BBBWWWWWWWWW")) return false;
    // if we've already got the white cross second STEP
    if (state.matches("....WW.W...........G........R....................Y....")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move white/red edge to top {}", state);
    // find the green/white edge
    EdgePosition p = Finder.findEdge(state, 'W', 'R');
    log.info("Found piece in position {}",p.name());
    switch (p) {
      // put pieces on the top row to the bottom
      case EDGE_TOP_RIGHT: // should never happen
      case EDGE_RIGHT_TOP:
        return "R2";
      case EDGE_TOP_FRONT: // should never happen
      case EDGE_FRONT_TOP: // should never happen
        return "F2";
      case EDGE_TOP_LEFT:
      case EDGE_LEFT_TOP:
        return "L2";
      case EDGE_TOP_BACK:
      case EDGE_BACK_TOP:
        return "B2";
      // front rotations
      case EDGE_RIGHT_FRONT:
        return "R'";
      case EDGE_BOTTOM_FRONT:
        return "D";
      case EDGE_LEFT_FRONT:
      case EDGE_FRONT_LEFT:
        return "F' D F";

      case EDGE_FRONT_RIGHT:
        return "R";
      case EDGE_FRONT_BOTTOM:
        return "D";
      // remaining middle rows to bottom
      case EDGE_RIGHT_BACK:
        return "R";
      case EDGE_BACK_RIGHT:
        return "R'";
      case EDGE_BACK_LEFT:
        case EDGE_LEFT_BACK:
        return "L'";
      // bottom to front
      case EDGE_BACK_BOTTOM:
        return "Fw D' Fw'";
      case EDGE_BOTTOM_BACK:
        return "D'";
      case EDGE_RIGHT_BOTTOM:
        return "D Fw D' Fw'";
      case EDGE_BOTTOM_RIGHT:
        return "Fw D2 Fw'";
      case EDGE_BOTTOM_LEFT:
        return "D2";
      case EDGE_LEFT_BOTTOM:
        return "D' Fw D' Fw'";
    }
    throw new SolverHaltException("Can't find green/red edge cube");
  }
}
