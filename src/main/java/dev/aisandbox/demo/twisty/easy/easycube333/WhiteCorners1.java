package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhiteCorners1 implements Solver3x3x3Algorithm {
  @Override
  public boolean isValid(String state) {
    // If we've already solved the white side and turned the cube over - ignore
    if (state.matches("....Y..........RRR......GGG......OOO......BBBWWWWWWWWW")) return false;
    // if we've already got the first top corner - ignore
    if (state.matches(".W.WWWWW..OO......GG........R........B...........Y....")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move white/orange/green edge to top/left/front {}", state);
    CornerPosition p = Finder.findCorner(state, 'W', 'O', 'G');
    log.info("Found piece in position {}", p.name());
    switch (p) {
        // ignore - already solved
      case CORNER_TOP_LEFT_FRONT:
        return "";
        // right place - wrong rotation
      case CORNER_FRONT_TOP_LEFT:
        return "L D L'";
      case CORNER_LEFT_FRONT_TOP:
        return "L D L'";
        // top row to bottom
      case CORNER_FRONT_RIGHT_TOP:
        return "R' D' R";
      case CORNER_RIGHT_TOP_FRONT:
      case CORNER_TOP_FRONT_RIGHT:
        return "R' D' R";

      case CORNER_RIGHT_BACK_TOP:
      case CORNER_TOP_RIGHT_BACK:
      case CORNER_BACK_TOP_RIGHT:
        return "R D R'";

      case CORNER_BACK_LEFT_TOP:
      case CORNER_TOP_BACK_LEFT:
      case CORNER_LEFT_TOP_BACK:
        return "L' D' L";
      // bottom row to correct place
      case CORNER_FRONT_BOTTOM_RIGHT:
        return "D'";
      case CORNER_BOTTOM_RIGHT_FRONT:
      case CORNER_RIGHT_FRONT_BOTTOM:
        return "D'";
      case CORNER_BACK_RIGHT_BOTTOM:
      case CORNER_BOTTOM_BACK_RIGHT:
      case CORNER_RIGHT_BOTTOM_BACK:
        return "D2";
      case CORNER_BACK_BOTTOM_LEFT:
      case CORNER_LEFT_BACK_BOTTOM:
      case CORNER_BOTTOM_LEFT_BACK:
        return "D";

      // lift to correct place
      case CORNER_FRONT_LEFT_BOTTOM:
        return "D L D' L'";
      case CORNER_LEFT_BOTTOM_FRONT:
        return "D' F' D F";
      case CORNER_BOTTOM_FRONT_LEFT:
        return "F' D F D2"; // recursive
    }
    throw new SolverHaltException("Can't find green/red edge cube");
  }
}
