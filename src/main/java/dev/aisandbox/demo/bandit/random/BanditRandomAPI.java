package dev.aisandbox.demo.bandit.random;

import dev.aisandbox.demo.bandit.api.BanditRequest;
import dev.aisandbox.demo.bandit.api.BanditResponse;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BanditRandomAPI {
  private Random rand = new Random(System.currentTimeMillis());

  @PostMapping(path = "/ai/bandit/random", produces = {MediaType.APPLICATION_JSON_VALUE})
  public BanditResponse getRandomResponse(@RequestBody BanditRequest req) {
    BanditResponse response = new BanditResponse();
    response.setArm(rand.nextInt(req.getBanditCount()));
    return response;
  }

}
