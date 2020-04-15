package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecondLayer1 implements Solver3x3x3Algorithm {

  public static final String SECOND_LEFT = "U' L' U L U F U' F'";
  public static final String SECOND_RIGHT = "U R U' R' U' F' U F";

  @Override
  public boolean isValid(String state) {
    // If we've already got the first edge - ignore
    if (state.matches("....Y.........RRRR...G..GGG......OOO......BBBWWWWWWWWW")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move Green/Red edge to second layer {}", state);
    EdgePosition p = Finder.findEdge(state, 'G', 'R');
    log.info("Found at {}",p.name());
    switch (p) {
        // already solved
      case EDGE_BACK_BOTTOM:
      case EDGE_FRONT_BOTTOM:
      case EDGE_LEFT_BOTTOM:
      case EDGE_BOTTOM_FRONT:
      case EDGE_BOTTOM_RIGHT:
      case EDGE_BOTTOM_BACK:
      case EDGE_BOTTOM_LEFT:
      case EDGE_RIGHT_BOTTOM:
      case EDGE_FRONT_LEFT:
        return "";

      case EDGE_TOP_FRONT:
        return "Dw " + SECOND_RIGHT + " Dw'";

      case EDGE_TOP_BACK:
        return "U2 Dw " + SECOND_RIGHT + " Dw'";

      case EDGE_TOP_LEFT:
        return "y' " + SECOND_RIGHT + " y";
      case EDGE_TOP_RIGHT:
        return "U";
      case EDGE_BACK_TOP:
        return "U2";
      case EDGE_BACK_LEFT:
        return "y' " + SECOND_LEFT + " y";
      case EDGE_BACK_RIGHT:
        return "y " + SECOND_RIGHT + " y'";

      case EDGE_FRONT_RIGHT:
        return SECOND_RIGHT;
      case EDGE_FRONT_TOP:
        return SECOND_LEFT;

      case EDGE_LEFT_TOP:
        return "U'";
      case EDGE_LEFT_BACK:
        return "y' " + SECOND_LEFT + " y";
      case EDGE_LEFT_FRONT:
        return SECOND_LEFT;

      case EDGE_RIGHT_TOP:
        return "U";
      case EDGE_RIGHT_BACK:
        return "y " + SECOND_RIGHT + " y'";
      case EDGE_RIGHT_FRONT:
        return SECOND_RIGHT;
    }
    return null;
  }
}
