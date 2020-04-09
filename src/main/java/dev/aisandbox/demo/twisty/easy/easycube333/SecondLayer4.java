package dev.aisandbox.demo.twisty.easy.easycube333;

import static dev.aisandbox.demo.twisty.easy.easycube333.SecondLayer1.SECOND_LEFT;
import static dev.aisandbox.demo.twisty.easy.easycube333.SecondLayer1.SECOND_RIGHT;

import dev.aisandbox.demo.twisty.easy.SolverException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecondLayer4 implements SolverAlgorithm {

  @Override
  public boolean isValid(String state) {
    // If we've already got the first four edges - ignore
    if (state.matches("....Y.......RRRRRR...GGGGGG...OOOOOO...BBBBBBWWWWWWWWW")) return false;
    // otherwise return true
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Trying to move Orange/Blue edge to second layer {}", state);
    EdgePosition p = Finder.findEdge(state, 'O', 'B');
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
      case EDGE_LEFT_FRONT:
      case EDGE_FRONT_RIGHT:
      case EDGE_RIGHT_FRONT:
      case EDGE_LEFT_BACK:
      case EDGE_BACK_LEFT:
      case EDGE_RIGHT_BACK:
        return "";

      case EDGE_FRONT_TOP:
        return "Dw' "+SECOND_RIGHT+" Dw";
      case EDGE_TOP_FRONT:
        return "Dw2 " + SECOND_LEFT + " Dw2";

      case EDGE_TOP_BACK:
        return "U2";
      case EDGE_TOP_LEFT:
        return "U'";
      case EDGE_TOP_RIGHT:
        return "U";
      case EDGE_BACK_TOP:
        return "U2";
      case EDGE_BACK_RIGHT:
        return "y " + SECOND_RIGHT + " y'";
      case EDGE_LEFT_TOP:
        return "U'";
      case EDGE_RIGHT_TOP:
        return "U";

    }
    return null;
  }

}
