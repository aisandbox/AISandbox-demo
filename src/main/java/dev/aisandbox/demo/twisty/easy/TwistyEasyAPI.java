package dev.aisandbox.demo.twisty.easy;

import dev.aisandbox.demo.twisty.api.TwistyRequest;
import dev.aisandbox.demo.twisty.api.TwistyResponse;
import dev.aisandbox.demo.twisty.easy.easycube2x2x2.Solver2x2x2;
import dev.aisandbox.demo.twisty.easy.easycube333.Solver3x3x3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
public class TwistyEasyAPI {

  private Random rand = new Random(System.currentTimeMillis());

  private final Solver3x3x3 cubeSolver3x3x3;
  private final Solver2x2x2 cubeSolver2x2x2;

  @Autowired
  public TwistyEasyAPI(Solver3x3x3 cubeSolver3x3x3, Solver2x2x2 cubeSolver2x2x2) {
    this.cubeSolver3x3x3 = cubeSolver3x3x3;
    this.cubeSolver2x2x2 = cubeSolver2x2x2;
  }

  @PostMapping(
      path = "/ai/twisty/easy",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public TwistyResponse getSolverStep(@RequestBody TwistyRequest req) throws SolverException {
    String moves = null;
    switch (req.getPuzzleType()) {
      case "Cube 3x3x3":
        moves = cubeSolver3x3x3.getMoves(req.getPuzzleType(), req.getState(), req.getMoves());
        break;
      case "Cube 2x2x2":
        moves = cubeSolver2x2x2.getMoves(req.getState());
        break;
      default:
        log.warn("Can't solve puzzle '{}'",req.getPuzzleType());
        throw new SolverHaltException("I don't know how to solve this puzzle.");

    }
    log.info("Returning moves {}", moves);
    return new TwistyResponse(moves);
  }
}
