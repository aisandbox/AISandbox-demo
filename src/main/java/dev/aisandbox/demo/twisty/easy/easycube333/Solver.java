package dev.aisandbox.demo.twisty.easy.easycube333;

import dev.aisandbox.demo.twisty.easy.SolverException;
import dev.aisandbox.demo.twisty.easy.SolverHaltException;
import dev.aisandbox.demo.twisty.easy.TwistySolver;
import dev.aisandbox.demo.twisty.easy.TwistyTools;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solver implements TwistySolver {

  List<SolverAlgorithm> algorithms =
      List.of(
          new Orientation(),
          new WhiteCrossGreen(),
          new WhiteCrossRed(),
          new WhiteCrossOrange(),
          new WhiteCrossBlue(),
          new WhiteCorners1(),
          new WhiteCorners2(),
          new WhiteCorners3(),
          new WhiteCorners4(),
          new SecondLayer1(),
          new SecondLayer2(),
          new SecondLayer3(),
          new SecondLayer4(),
          new YellowCross(),
          new YellowEdge1(),
          new YellowEdge2(),
          new YellowEdge3());

  @Override
  public String getPuzzleType() {
    return TwistyTools.CUBE_333;
  }

  @Override
  public String getAlgorithmName() {
    return "Easy Solver";
  }

  @Override
  public String getMoves(String puzzleType, String state, List<String> availableMoves)
      throws SolverException {
    log.info("Solving from state {}", state);
    for (SolverAlgorithm algorithm : algorithms) {
      if (algorithm.isValid(state)) {
        log.info("claimed by {}", algorithm.getClass().getSimpleName());
        String moves = algorithm.getMoves(state);
        log.info("Worked out moves {}", moves);
        return moves;
      }
    }
    log.info("No algorithm claims this");
    // halt solve
    throw new SolverHaltException("Dont know how to solve - halting");
  }
}
