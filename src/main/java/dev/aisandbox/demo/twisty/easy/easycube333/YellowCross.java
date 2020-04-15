package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YellowCross implements Solver3x3x3Algorithm {

  public static final String CROSS_MOVE = "F R U R' U' F'";

  @Override
  public boolean isValid(String state) {
    if (state.matches(".Y.YYY.Y....RRRRRR...GGGGGG...OOOOOO...BBBBBBWWWWWWWWW"))
      return false; // cross already solved
    return true;
  }

  @Override
  public String getMoves(String state) throws SolverException {
    log.info("Solving yellow cross for state {}",state);
    // check for horizontal bar
    if (state.matches("...YYY......RRRRRR...GGGGGG...OOOOOO...BBBBBBWWWWWWWWW")) return CROSS_MOVE;
    // vertical bar
    if (state.matches(".Y..Y..Y....RRRRRR...GGGGGG...OOOOOO...BBBBBBWWWWWWWWW")) return "U " + CROSS_MOVE;
    // TL Cross
    if (state.matches(".Y.YY.......RRRRRR...GGGGGG...OOOOOO...BBBBBBWWWWWWWWW")) return CROSS_MOVE;
    // TR Cross
    if (state.matches(".Y..YY......RRRRRR...GGGGGG...OOOOOO...BBBBBBWWWWWWWWW"))
      return "U' "+CROSS_MOVE;
    // BL Cross
    if (state.matches("...YY..Y....RRRRRR...GGGGGG...OOOOOO...BBBBBBWWWWWWWWW"))
      return "U " + CROSS_MOVE;
    // BR Cross
    if (state.matches("....YY.Y....RRRRRR...GGGGGG...OOOOOO...BBBBBBWWWWWWWWW"))
      return "U2 " + CROSS_MOVE;
    return CROSS_MOVE;
  }
}
