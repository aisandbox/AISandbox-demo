package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhiteCrossBlue implements SolverAlgorithm {
  @Override
  public boolean isValid(String state) {
    // If we've already solved the white side and turned the cube over - ignore
    if (state.matches("....Y..........RRR......GGG......OOO......BBBWWWWWWWWW")) return false;
    // if we've already got the white cross forth STEP
    if (state.matches(".W.WWW.W..O........G........R........B...........Y....")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move white/blue edge to top {}", state);
    // find the green/white edge
    EdgePosition p = Finder.findEdge(state, 'W', 'B');
    log.info("Found piece in position {}", p.name());
    switch (p) {
        // put pieces on the top row to the bottom
      case EDGE_TOP_RIGHT: // should never happen
      case EDGE_RIGHT_TOP: // should never happen
      case EDGE_TOP_FRONT: // should never happen
      case EDGE_FRONT_TOP: // should never happen
      case EDGE_TOP_LEFT: // should never happen
      case EDGE_LEFT_TOP: // should never happen
      case EDGE_TOP_BACK: // correct place - should never happen
        throw new SolverHaltException("Solver confused. Halting");
      case EDGE_BACK_TOP: // correct place, wrong way round, move to bottom
        return "B2";
        // front rotations
      case EDGE_RIGHT_FRONT:
        return "U2 F' U2";
      case EDGE_BOTTOM_FRONT:
        return "U2 F2 U2";
      case EDGE_LEFT_FRONT: // move to bottom
        return "U2 F U2";
      case EDGE_FRONT_LEFT:
        return "U' L' U";
      case EDGE_FRONT_RIGHT:
        return "U R U'";
      case EDGE_FRONT_BOTTOM:
        return "D' Rw D' Rw'";
      case EDGE_RIGHT_BACK:
      case EDGE_BACK_LEFT:
        return "B";
      case EDGE_BACK_RIGHT:
      case EDGE_LEFT_BACK:
        return "B'";
      // bottom to front
      case EDGE_BACK_BOTTOM:
        return "D Rw D' Fw";
      case EDGE_BOTTOM_BACK:
        return "Rw D2 Rw'";
      case EDGE_RIGHT_BOTTOM:
        return "Lw' D Lw";
      case EDGE_BOTTOM_RIGHT:
        return "D";
      case EDGE_BOTTOM_LEFT:
        return "D'";
      case EDGE_LEFT_BOTTOM:
        return "Rw D' Rw'";
    }
    throw new SolverHaltException("Can't find white/Blue edge cube");
  }
}
