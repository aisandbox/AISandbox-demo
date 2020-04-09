package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhiteCrossOrange implements SolverAlgorithm {
  @Override
  public boolean isValid(String state) {
    // If we've already solved the white side and turned the cube over - ignore
    if (state.matches("....Y..........RRR......GGG......OOO......BBBWWWWWWWWW")) return false;
    // if we've already got the white cross second STEP
    if (state.matches("...WWW.W..O........G........R....................Y....")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move white/orange edge to top {}", state);
    // find the green/white edge
    EdgePosition p = Finder.findEdge(state, 'W', 'O');
    log.info("Found piece in position {}", p.name());
    switch (p) {
        // put pieces on the top row to the bottom
      case EDGE_TOP_RIGHT: // should never happen
      case EDGE_RIGHT_TOP: // should never happen
      case EDGE_TOP_FRONT: // should never happen
      case EDGE_FRONT_TOP: // should never happen
      case EDGE_TOP_LEFT: // should never happen
        throw new SolverHaltException("Solver confused. Halting");
      case EDGE_LEFT_TOP: // correct place - wrong way round
        return "Fw' D Fw";
      case EDGE_TOP_BACK: // in back position - move to bottom row
      case EDGE_BACK_TOP:
        return "B2";
      // front rotations
      case EDGE_RIGHT_FRONT:
        return "U' F' U";
      case EDGE_BOTTOM_FRONT:
      case EDGE_FRONT_BOTTOM:
        return "Bw D' Bw'";
      case EDGE_LEFT_FRONT: // move to bottom
        return "L";
      case EDGE_FRONT_LEFT:
        return "L'";
      case EDGE_FRONT_RIGHT:
        return "R' D' R";
      case EDGE_RIGHT_BACK:
      case EDGE_BACK_RIGHT:
        return "B'";
      case EDGE_BACK_LEFT:
      case EDGE_LEFT_BACK:
        return "B";
      // bottom to front
      case EDGE_BACK_BOTTOM:
        return "Fw' D Fw";
      case EDGE_BOTTOM_BACK:
        return "D Fw' D2 Fw";
      case EDGE_RIGHT_BOTTOM:
        return "D'";
      case EDGE_BOTTOM_RIGHT:
      case EDGE_LEFT_BOTTOM:
        return "D";
      case EDGE_BOTTOM_LEFT:
        return "Fw' D2 Fw";
    }
    throw new SolverHaltException("Can't find green/Orange edge cube");
  }
}
