package dev.aisandbox.demo.maze.random;

import dev.aisandbox.demo.maze.api.Config;
import dev.aisandbox.demo.maze.api.MazeRequest;
import dev.aisandbox.demo.maze.api.MazeResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.logging.Logger;

@RestController
public class MazeRandomAPI {

    private static final Logger LOG = Logger.getLogger(MazeRandomAPI.class.getName());

    private Random rand = new Random(System.currentTimeMillis());

    @PostMapping(path = "/ai/maze/random", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MazeResponse getRandomResponse(@RequestBody MazeRequest req) {
        MazeResponse response = new MazeResponse();
        response.setMove(randomMove(req.getConfig()));
        return response;
    }

    private String randomMove(Config config) {
        LOG.info("Picking random move from "+String.join(",", config.getValidMoves()));
        return config.getValidMoves()[rand.nextInt(config.getValidMoves().length)];
    }

}
