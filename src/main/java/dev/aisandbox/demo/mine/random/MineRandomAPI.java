package dev.aisandbox.demo.mine.random;


import dev.aisandbox.demo.mine.MineUtil;
import dev.aisandbox.demo.mine.api.MineRequest;
import dev.aisandbox.demo.mine.api.MineResponse;
import dev.aisandbox.demo.mine.api.Move;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.logging.Logger;

@RestController
public class MineRandomAPI {

    private static final Logger LOG = Logger.getLogger(MineRandomAPI.class.getName());

    private Random rand = new Random(System.currentTimeMillis());

    @PostMapping(path = "/ai/mine/random", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MineResponse getRandomResponse(@RequestBody MineRequest req) {
        char[][] grid = MineUtil.readMineGrid(req.getBoard());
        int width = grid.length;
        int height = grid[0].length;
        MineResponse response = new MineResponse();
        // pick an uncovered - unflagged square
        Move move = null;
        while (move==null) {
            int x=rand.nextInt(width);
            int y=rand.nextInt(height);
            char c = grid[x][y];
            if (c=='#') {
                move = new Move(x,y,rand.nextBoolean());
            }
        }
        LOG.fine("Random move "+move.toString());
        response.setMoves(new Move[] {move});
        return response;
    }
}
