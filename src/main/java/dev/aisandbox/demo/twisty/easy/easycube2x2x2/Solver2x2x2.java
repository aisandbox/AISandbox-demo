package dev.aisandbox.demo.twisty.easy.easycube2x2x2;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Solver2x2x2 {

  public static final String YELLOW_CORNER_ROTATE = "R' D' R D R' D' R D";

  public String getMoves(String state) throws SolverException {
    // solve first
    if (!state.matches("..W..O..G...............")) {
      return solveFirstCorner(state);
    } else if (!state.matches("..WW.O..GG..R...........")) {
      return solveSecondCorner(state);
    } else if (!state.matches("W.WWOO..GG..R....B......")) {
      return solveThirdCorner(state);
    } else if (!state.matches("WWWWOO..GG..RR..BB......")) {
      return solveForthCorner(state);
    } else {
      // find yellow corners
      CornerPosition p1 = Finder.findCorner(state, 'Y', 'B', 'R');
      CornerPosition p2 = Finder.findCorner(state, 'Y', 'O', 'B');
      CornerPosition p3 = Finder.findCorner(state, 'Y', 'G', 'O');
      CornerPosition p4 = Finder.findCorner(state, 'Y', 'R', 'G');
      // check first corner is correct
      if (!(p1 == CornerPosition.CORNER_BACK_RIGHT_BOTTOM
          || p1 == CornerPosition.CORNER_RIGHT_BOTTOM_BACK
          || p1 == CornerPosition.CORNER_BOTTOM_BACK_RIGHT)) {
        return rotateYellow1(p1);
        // check third corner is correct
      } else if (!(p3 == CornerPosition.CORNER_BOTTOM_FRONT_LEFT
          || p3 == CornerPosition.CORNER_FRONT_LEFT_BOTTOM
          || p3 == CornerPosition.CORNER_LEFT_BOTTOM_FRONT)) {
        // Y/G/O is in the wrong place, rotate until correct
        log.info("Cycling yellow corners from {}", state);
        return "x2 U R U' L' U R' U' L x2";
        // check second and forth corners
      } else if (!(p2 == CornerPosition.CORNER_BOTTOM_LEFT_BACK
          || p2 == CornerPosition.CORNER_LEFT_BACK_BOTTOM
          || p2 == CornerPosition.CORNER_BACK_BOTTOM_LEFT)) {
        log.info("Swapping final two yellow corners from {}", state);
        return "x2 y' F L F L' D' L' D y x2";
      } else {
        log.info("Rotating yellow corners from {}",state);
        return rotateYellowCorners(p1, p2, p3, p4);
      }
    }
  }

  public String rotateYellowCorners(
      CornerPosition p1, CornerPosition p2, CornerPosition p3, CornerPosition p4)
      throws SolverException {
    log.info(
        "Found yellow/blue/red {}, yellow/orange/blue {}, yellow/green/orange {}, yellow/red/green {}",
        p1,
        p2,
        p3,
        p4);
    // work out how many rotations each corner needs
    int r1, r2, r3, r4;
    assert p1 != null;
    switch (p1) {
      case CORNER_RIGHT_BOTTOM_BACK:
        r1 = 1;
        break;
      case CORNER_BACK_RIGHT_BOTTOM:
        r1 = 2;
        break;
      default:
        r1 = 0;
    }
    switch (p2) {
      case CORNER_BACK_BOTTOM_LEFT:
        r2 = 1;
        break;
      case CORNER_LEFT_BACK_BOTTOM:
        r2 = 2;
        break;
      default:
        r2 = 0;
    }
    switch (p3) {
      case CORNER_LEFT_BOTTOM_FRONT:
        r3 = 1;
        break;
      case CORNER_FRONT_LEFT_BOTTOM:
        r3 = 2;
        break;
      default:
        r3 = 0;
    }
    switch (p4) {
      case CORNER_FRONT_BOTTOM_RIGHT:
        r4 = 1;
        break;
      case CORNER_RIGHT_FRONT_BOTTOM:
        r4 = 2;
        break;
      default:
        r4 = 0;
    }
    log.info(
        "Rotating yellow/blue/red {} times, yellow/orange/blue {} times, yellow/green/orange {} times, yellow/red/green {} times.",
        r1,
        r2,
        r3,
        r4);
    StringBuilder sb = new StringBuilder();
    sb.append("x2 ");
    // rotate r1
    for (int i = 0; i < r1; i++) {
      sb.append(YELLOW_CORNER_ROTATE);
      sb.append(" ");
    }
    // rotate top
    sb.append("U ");

    // rotate r4
    for (int i = 0; i < r4; i++) {
      sb.append(YELLOW_CORNER_ROTATE);
      sb.append(" ");
    }
    // rotate top
    sb.append("U ");

    // rotate r3
    for (int i = 0; i < r3; i++) {
      sb.append(YELLOW_CORNER_ROTATE);
      sb.append(" ");
    }
    // rotate top
    sb.append("U ");

    // rotate r2
    for (int i = 0; i < r2; i++) {
      sb.append(YELLOW_CORNER_ROTATE);
      sb.append(" ");
    }
    // rotate top
    sb.append("U ");
    // spin back to normal
    sb.append("x2");
    // simplify commands
    return sb.toString().replaceAll("  ", " ").trim();
  }

  public String rotateYellow1(CornerPosition position) throws SolverHaltException {
    log.info("Rotating first yellow corner into place from {}", position.name());
    switch (position) {
      case CORNER_FRONT_LEFT_BOTTOM:
      case CORNER_LEFT_BOTTOM_FRONT:
      case CORNER_BOTTOM_FRONT_LEFT:
        return "D2";
      case CORNER_RIGHT_FRONT_BOTTOM:
      case CORNER_FRONT_BOTTOM_RIGHT:
      case CORNER_BOTTOM_RIGHT_FRONT:
        return "D";
      case CORNER_LEFT_BACK_BOTTOM:
      case CORNER_BACK_BOTTOM_LEFT:
      case CORNER_BOTTOM_LEFT_BACK:
        return "D'";
      default:
        throw new SolverHaltException("Dont know how to rotate from " + position.name());
    }
  }

  public String solveForthCorner(String state) throws SolverHaltException {
    log.info("Solving forth corner from {}", state);
    CornerPosition position = Finder.findCorner(state, 'B', 'W', 'R');
    switch (position) {
        // top right back (wrong alignment) - move to bottom
      case CORNER_TOP_RIGHT_BACK:
      case CORNER_RIGHT_BACK_TOP:
        return "R D R'";
        // bottom row to correct place
      case CORNER_RIGHT_FRONT_BOTTOM:
      case CORNER_FRONT_BOTTOM_RIGHT:
      case CORNER_BOTTOM_RIGHT_FRONT:
        return "D";
      case CORNER_LEFT_BOTTOM_FRONT:
      case CORNER_BOTTOM_FRONT_LEFT:
      case CORNER_FRONT_LEFT_BOTTOM:
        return "D2";
      case CORNER_LEFT_BACK_BOTTOM:
      case CORNER_BACK_BOTTOM_LEFT:
      case CORNER_BOTTOM_LEFT_BACK:
        return "D'";
      case CORNER_BOTTOM_BACK_RIGHT:
        return "F' R' F";
      case CORNER_BACK_RIGHT_BOTTOM:
        return "L B L'";
      case CORNER_RIGHT_BOTTOM_BACK:
        return "B' D2 B";
      default:
        throw new SolverHaltException("Solving an already solved square");
    }
  }

  public String solveThirdCorner(String state) throws SolverHaltException {
    log.info("Solving third corner from {}", state);
    CornerPosition postition = Finder.findCorner(state, 'O', 'W', 'B');
    switch (postition) {
        // top back - move to bottom
      case CORNER_TOP_BACK_LEFT:
      case CORNER_BACK_LEFT_TOP:

      case CORNER_RIGHT_BACK_TOP:
      case CORNER_BACK_TOP_RIGHT:
      case CORNER_TOP_RIGHT_BACK:
        return "B2";
        // move bottom row to back left
      case CORNER_FRONT_BOTTOM_RIGHT:
      case CORNER_BOTTOM_RIGHT_FRONT:
      case CORNER_RIGHT_FRONT_BOTTOM:
        return "D2";
      case CORNER_FRONT_LEFT_BOTTOM:
      case CORNER_LEFT_BOTTOM_FRONT:
      case CORNER_BOTTOM_FRONT_LEFT:
        return "D'";
      case CORNER_RIGHT_BOTTOM_BACK:
      case CORNER_BOTTOM_BACK_RIGHT:
      case CORNER_BACK_RIGHT_BOTTOM:
        return "D";
        // back left up - correct orientation
      case CORNER_BOTTOM_LEFT_BACK:
        return "B'";
      case CORNER_LEFT_BACK_BOTTOM:
        return "F L F'";
      case CORNER_BACK_BOTTOM_LEFT:
        return "D' B2";
      default:
        throw new SolverHaltException("Solving an already solved square");
    }
  }

  public String solveSecondCorner(String state) throws SolverHaltException {
    log.info("Solving second corner from {}", state);
    CornerPosition position = Finder.findCorner(state, 'R', 'W', 'G');
    switch (position) {
        // top back - rotate to bottom
      case CORNER_BACK_TOP_RIGHT:
      case CORNER_TOP_RIGHT_BACK:
      case CORNER_RIGHT_BACK_TOP:
      case CORNER_LEFT_TOP_BACK:
      case CORNER_TOP_BACK_LEFT:
      case CORNER_BACK_LEFT_TOP:
        return "B2";
        // top right (wrong face) - rotate to bottom
      case CORNER_TOP_FRONT_RIGHT:
      case CORNER_FRONT_RIGHT_TOP:
        return "R'";
        // rotate bottom row to correct place
      case CORNER_BACK_RIGHT_BOTTOM:
      case CORNER_RIGHT_BOTTOM_BACK:
      case CORNER_BOTTOM_BACK_RIGHT:
        return "D'";
      case CORNER_LEFT_BACK_BOTTOM:
      case CORNER_BACK_BOTTOM_LEFT:
      case CORNER_BOTTOM_LEFT_BACK:
        return "D2";
      case CORNER_LEFT_BOTTOM_FRONT:
      case CORNER_BOTTOM_FRONT_LEFT:
      case CORNER_FRONT_LEFT_BOTTOM:
        return "D";
        // rotate front right bottom up with correct orientation
      case CORNER_RIGHT_FRONT_BOTTOM:
        return "R";
      case CORNER_FRONT_BOTTOM_RIGHT:
        return "D R2";
      case CORNER_BOTTOM_RIGHT_FRONT:
        return "R' D' R";
      default:
        throw new SolverHaltException("Solving an already solved square");
    }
  }

  public String solveFirstCorner(String state) throws SolverHaltException {
    log.info("Solving first corner from {}", state);
    CornerPosition position = Finder.findCorner(state, 'G', 'W', 'O');
    switch (position) {
      case CORNER_BACK_BOTTOM_LEFT:
        return "x2";
      case CORNER_BACK_LEFT_TOP:
        return "z y2";
      case CORNER_BACK_RIGHT_BOTTOM:
        return "z' y2";
      case CORNER_BACK_TOP_RIGHT:
        return "y2";
      case CORNER_BOTTOM_BACK_RIGHT:
        return "x z2";
      case CORNER_BOTTOM_FRONT_LEFT:
        return "x";
      case CORNER_BOTTOM_LEFT_BACK:
        return "z y'";
      case CORNER_BOTTOM_RIGHT_FRONT:
        return "z' y";
      case CORNER_FRONT_BOTTOM_RIGHT:
        return "z2";
      case CORNER_FRONT_LEFT_BOTTOM:
        return "z";
      case CORNER_FRONT_RIGHT_TOP:
        return "z'";
      case CORNER_LEFT_BACK_BOTTOM:
        return "x' y'";
      case CORNER_LEFT_BOTTOM_FRONT:
        return "x2 y'";
      case CORNER_LEFT_FRONT_TOP:
        return "x y'";
      case CORNER_LEFT_TOP_BACK:
        return "y'";
      case CORNER_RIGHT_BACK_TOP:
        return "y z'";
      case CORNER_RIGHT_BOTTOM_BACK:
        return "y z2";
      case CORNER_RIGHT_FRONT_BOTTOM:
        return "x y";
      case CORNER_RIGHT_TOP_FRONT:
        return "y";
      case CORNER_TOP_BACK_LEFT:
        return "x'";
      case CORNER_TOP_FRONT_RIGHT:
        return "x y2";
      case CORNER_TOP_LEFT_FRONT:
        return "z y";
      case CORNER_TOP_RIGHT_BACK:
        return "z' y'";
      default:
        throw new SolverHaltException("Solving an already solved square");
    }
  }
}
