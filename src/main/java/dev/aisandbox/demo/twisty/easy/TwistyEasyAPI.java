package dev.aisandbox.demo.twisty.easy;

import dev.aisandbox.demo.twisty.api.TwistyRequest;
import dev.aisandbox.demo.twisty.api.TwistyResponse;
import dev.aisandbox.demo.twisty.easy.easycube333.Solver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class TwistyEasyAPI {

  private Random rand = new Random(System.currentTimeMillis());

  Solver cubeSolver = new Solver();

  @PostMapping(
      path = "/ai/twisty/easy",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public TwistyResponse getRandomResponse(@RequestBody TwistyRequest req) throws SolverException {
    String moves = cubeSolver.getMoves(req.getPuzzleType(), req.getState(), req.getMoves());
    log.info("Returning moves {}", moves);
    return new TwistyResponse(moves);
  }
}
