package dev.aisandbox.demo.maze.random;

import dev.aisandbox.demo.maze.api.Config;
import dev.aisandbox.demo.maze.api.MazeRequest;
import dev.aisandbox.demo.maze.api.MazeResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class MazeRandomAPI {

    private Random rand = new Random(System.currentTimeMillis());

    @PostMapping(path = "/ai/maze/random", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MazeResponse getRandomResponse(MazeRequest req) {
        MazeResponse response = new MazeResponse();
        response.setMove(randomMove(req.getConfig()));
        return response;
    }

    private String randomMove(Config config) {
        return config.getValidMoves()[rand.nextInt(config.getValidMoves().length)];
    }

}
