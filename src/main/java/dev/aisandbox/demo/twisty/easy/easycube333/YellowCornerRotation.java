package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YellowCornerRotation implements SolverAlgorithm {

  public static final String YELLOW_CORNER_ROTATE = "R' D' R D R' D' R D";

  @Override
  public boolean isValid(String state) {
    return !state.equals("YYYYYYYYYRRRRRRRRRGGGGGGGGGOOOOOOOOOBBBBBBBBBWWWWWWWWW");
  }

  @Override
  public String getMoves(String state) throws SolverException {
    // FIND TOP CORNERS
    CornerPosition p1 = Finder.findCorner(state, 'Y', 'R', 'G');
    CornerPosition p2 = Finder.findCorner(state, 'Y', 'B', 'R');
    CornerPosition p3 = Finder.findCorner(state, 'Y', 'O', 'B');
    CornerPosition p4 = Finder.findCorner(state, 'Y', 'G', 'O');
    log.info("Found yellow/red/green {}, yellow/blue/red {}, yellow/orange/blue {}, yellow/green/orange {}",p1,p2,p3,p4);
    // work out how many rotations each corner needs
    int r1, r2, r3, r4;
    assert p1 != null;
    switch (p1) {
      case CORNER_LEFT_FRONT_TOP:
        r1 = 2;
        break;
      case CORNER_FRONT_TOP_LEFT:
        r1 = 1;
        break;
      default:
        r1 = 0;
    }
    switch (p2) {
      case CORNER_BACK_LEFT_TOP:
        r2 = 2;
        break;
      case CORNER_LEFT_TOP_BACK:
        r2 = 1;
        break;
      default:
        r2 = 0;
    }
    switch (p3) {
      case CORNER_RIGHT_BACK_TOP:
        r3 = 2;
        break;
      case CORNER_BACK_TOP_RIGHT:
        r3 = 1;
        break;
      default:
        r3 = 0;
    }
    switch (p4) {
      case CORNER_FRONT_RIGHT_TOP:
        r4 = 2;
        break;
      case CORNER_RIGHT_TOP_FRONT:
        r4 = 1;
        break;
      default:
        r4 = 0;
    }
    log.info("Rotating yellow/red/green {} times, yellow/blue/red {} times, yellow/orange/blue {} times, yellow/green/orange {} times",r1,r2,r3,r4);
    StringBuilder sb = new StringBuilder();

    // rotate r4
    for (int i = 0; i < r4; i++) {
      sb.append(YELLOW_CORNER_ROTATE);
      sb.append(" ");
    }
    // rotate top
    sb.append("U' ");

    // rotate r1
    for (int i = 0; i < r1; i++) {
      sb.append(YELLOW_CORNER_ROTATE);
      sb.append(" ");
    }
    // rotate top
    sb.append("U' ");

    // rotate r2
    for (int i = 0; i < r2; i++) {
      sb.append(YELLOW_CORNER_ROTATE);
      sb.append(" ");
    }
    // rotate top
    sb.append("U' ");

    // rotate r3
    for (int i = 0; i < r3; i++) {
      sb.append(YELLOW_CORNER_ROTATE);
      sb.append(" ");
    }
    // rotate top
    sb.append("U' ");
    // simplify commands
    return sb.toString().replaceAll("  ", " ").trim();
  }
}
