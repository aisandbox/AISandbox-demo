package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WhiteCorners2 implements Solver3x3x3Algorithm {
  @Override
  public boolean isValid(String state) {
    // If we've already solved the white side and turned the cube over - ignore
    if (state.matches("....Y..........RRR......GGG......OOO......BBBWWWWWWWWW")) return false;
    // if we've already got the second top corner - ignore
    if (state.matches(".W.WWWWWW.OO......GGG......RR........B...........Y....")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move white/green/red corner to top/front/right {}", state);
    CornerPosition p = Finder.findCorner(state, 'W', 'G', 'R');
    log.info("Found piece in position {}", p.name());
    switch (p) {
        // first corner - already solved
      case CORNER_FRONT_TOP_LEFT:
      case CORNER_TOP_LEFT_FRONT:
      case CORNER_LEFT_FRONT_TOP:
        return "";

      case CORNER_TOP_FRONT_RIGHT: // solved state
        return "";

      case CORNER_FRONT_RIGHT_TOP: // correct place wrong rotation - move to bottom
        return "R' D' R";
      case CORNER_RIGHT_TOP_FRONT:
        return "R' D' R";

      case CORNER_RIGHT_BACK_TOP:
      case CORNER_BACK_TOP_RIGHT:
      case CORNER_TOP_RIGHT_BACK: // move to bottom
        return "B' D' B";

      case CORNER_BACK_LEFT_TOP:
      case CORNER_LEFT_TOP_BACK:
      case CORNER_TOP_BACK_LEFT:// move to bottom
        return "L' D2 L";

      case CORNER_FRONT_BOTTOM_RIGHT:
        return "D' R' D R";
      case CORNER_BOTTOM_RIGHT_FRONT:
        return "F D2 F' D2 R' D R";
      case CORNER_RIGHT_FRONT_BOTTOM:
        return "D F D' F'";

      case CORNER_FRONT_LEFT_BOTTOM:
      case CORNER_LEFT_BOTTOM_FRONT:
      case CORNER_BOTTOM_FRONT_LEFT:
        return "D";

      case CORNER_BACK_RIGHT_BOTTOM:
      case CORNER_RIGHT_BOTTOM_BACK:
      case CORNER_BOTTOM_BACK_RIGHT:
        return "D'";

      case CORNER_BACK_BOTTOM_LEFT:
      case CORNER_BOTTOM_LEFT_BACK:
      case CORNER_LEFT_BACK_BOTTOM:
        return "D2";
    }
    throw new SolverHaltException("Can't find white/green/red corner cube");
  }
}
