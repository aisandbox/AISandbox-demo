package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhiteCorners4 implements SolverAlgorithm {
  @Override
  public boolean isValid(String state) {
    // If we've already solved the white side and turned the cube over - ignore
    if (state.matches("....Y..........RRR......GGG......OOO......BBBWWWWWWWWW")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move white/red/blue corner to top/right/back {}", state);
    CornerPosition p = Finder.findCorner(state, 'W', 'R', 'B');
    log.info("Found piece in position {}", p.name());
    switch (p) {
        // first three corners - already solved
      case CORNER_FRONT_TOP_LEFT:
      case CORNER_TOP_LEFT_FRONT:
      case CORNER_LEFT_FRONT_TOP:

      case CORNER_TOP_FRONT_RIGHT:
      case CORNER_FRONT_RIGHT_TOP:
      case CORNER_RIGHT_TOP_FRONT:

      case CORNER_BACK_LEFT_TOP:
      case CORNER_LEFT_TOP_BACK:
      case CORNER_TOP_BACK_LEFT: // solved state

      case CORNER_TOP_RIGHT_BACK: // top layer is solved, turn over for next step
        return "z2";
        // right position wrong rotation, move to bottom
      case CORNER_RIGHT_BACK_TOP:
      case CORNER_BACK_TOP_RIGHT:
        return "R D R'";

      case CORNER_FRONT_BOTTOM_RIGHT:
      case CORNER_BOTTOM_RIGHT_FRONT:
      case CORNER_RIGHT_FRONT_BOTTOM: // move from front right corner to back
        return "D";

      case CORNER_FRONT_LEFT_BOTTOM:
      case CORNER_LEFT_BOTTOM_FRONT:
      case CORNER_BOTTOM_FRONT_LEFT:
        return "D2";

      case CORNER_BACK_BOTTOM_LEFT:

      case CORNER_BOTTOM_LEFT_BACK:
      case CORNER_LEFT_BACK_BOTTOM:
        return "D'";

        // move up
      case CORNER_BACK_RIGHT_BOTTOM:
        return "Fw' R' Fw";
      case CORNER_RIGHT_BOTTOM_BACK:
        return "D' B' D B";
      case CORNER_BOTTOM_BACK_RIGHT:
        return "B' D B R D2 R'";
    }
    throw new SolverHaltException("Can't find white/red/blue corner cube");
  }
}
