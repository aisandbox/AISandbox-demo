package dev.aisandbox.demo.twisty.random;

import dev.aisandbox.demo.twisty.api.TwistyRequest;
import dev.aisandbox.demo.twisty.api.TwistyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
public class TwistyRandomAPI {

    private Random rand = new Random(System.currentTimeMillis());

    @PostMapping(path = "/ai/twisty/random", produces = {MediaType.APPLICATION_JSON_VALUE})
    public TwistyResponse getRandomResponse(@RequestBody TwistyRequest req) {
        // pick random move
        String move = req.getMoves().get(rand.nextInt(req.getMoves().size()));
        log.info("Selecting random move {} from list {}",move,req.getMoves());
        return new TwistyResponse(move);
    }

}
