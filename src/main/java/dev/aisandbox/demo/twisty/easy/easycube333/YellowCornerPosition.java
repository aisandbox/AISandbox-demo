package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YellowCornerPosition implements SolverAlgorithm {

  public static final String YELLOW_CORNER_SWITCH = "U R U' L' U R' U' L";

  @Override
  public boolean isValid(String state) {
    // FIND TOP CORNERS
    CornerPosition p1 = Finder.findCorner(state, 'Y', 'R', 'G');
    CornerPosition p2 = Finder.findCorner(state, 'Y', 'B', 'R');
    CornerPosition p3 = Finder.findCorner(state, 'Y', 'O', 'B');
    CornerPosition p4 = Finder.findCorner(state, 'Y', 'G', 'O');
    // if corners are in the correct place then reject this state
    if ((p1 == CornerPosition.CORNER_TOP_LEFT_FRONT
            || p1 == CornerPosition.CORNER_LEFT_FRONT_TOP
            || p1 == CornerPosition.CORNER_FRONT_TOP_LEFT)
        && (p2 == CornerPosition.CORNER_TOP_BACK_LEFT
            || p2 == CornerPosition.CORNER_BACK_LEFT_TOP
            || p2 == CornerPosition.CORNER_LEFT_TOP_BACK)
        && (p3 == CornerPosition.CORNER_TOP_RIGHT_BACK
            || p3 == CornerPosition.CORNER_RIGHT_BACK_TOP
            || p3 == CornerPosition.CORNER_BACK_TOP_RIGHT)
        && (p4 == CornerPosition.CORNER_TOP_FRONT_RIGHT
            || p4 == CornerPosition.CORNER_FRONT_RIGHT_TOP
            || p4 == CornerPosition.CORNER_RIGHT_TOP_FRONT)) return false;
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    // FIND TOP CORNERS
    CornerPosition p4 = Finder.findCorner(state, 'Y', 'G', 'O');
    log.info("Found Yellow/Green/Orange corner @ {}",p4);
    // if yellow/green/orange is in position, just keep rotating until correct
    if (p4 == CornerPosition.CORNER_TOP_FRONT_RIGHT
        || p4 == CornerPosition.CORNER_FRONT_RIGHT_TOP
        || p4 == CornerPosition.CORNER_RIGHT_TOP_FRONT) {
      log.info("Performing corner switch");
      return YELLOW_CORNER_SWITCH;
    }
    // if yellow/green/orange is top/front/left, rotate cube then align
    if (p4 == CornerPosition.CORNER_TOP_LEFT_FRONT
        || p4 == CornerPosition.CORNER_LEFT_FRONT_TOP
        || p4 == CornerPosition.CORNER_FRONT_TOP_LEFT) {
      log.info("Rotating y then corner switch");
      return "y "+YELLOW_CORNER_SWITCH+" y'";
    }
    // if yellow/green/orange is top/back/left, rotate cube then align
    if (p4 == CornerPosition.CORNER_TOP_BACK_LEFT
        || p4 == CornerPosition.CORNER_BACK_LEFT_TOP
        || p4 == CornerPosition.CORNER_LEFT_TOP_BACK) {
      log.info("Rotating y then corner switch twice");
      return "y "+YELLOW_CORNER_SWITCH+" "+YELLOW_CORNER_SWITCH+" y'";
    }
    // yellow/green/orange must by top/right/back
    log.info("Rotating y twice, then corner switch twice");
    return "y2 "+YELLOW_CORNER_SWITCH+" "+YELLOW_CORNER_SWITCH+" y2";
  }
}
