package dev.aisandbox.demo.mine.rules;

import dev.aisandbox.demo.mine.MineUtil;
import dev.aisandbox.demo.mine.api.MineRequest;
import dev.aisandbox.demo.mine.api.MineResponse;
import dev.aisandbox.demo.mine.api.Move;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MineRulesAPI {

    private static final Logger LOG = Logger.getLogger(MineRulesAPI.class.getName());

    private Random rand = new Random(System.currentTimeMillis());

    @PostMapping(path = "/ai/mine/rules", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MineResponse getRandomResponse(@RequestBody MineRequest req) {
        char[][] grid = MineUtil.readMineGrid(req.getBoard());
        int width = grid.length;
        int height = grid[0].length;
        MineResponse response = new MineResponse();
        List<Move> moves = new ArrayList<>();
        // RULE 1 - Automatic Flagging & Reveal
        moves.addAll(applyAutomaticFlaggingAndReveal(grid,width,height));
        LOG.log(Level.INFO,"{0} moves found after rule 1",new Object[] {moves.size()});
        // fallback position - place random move
        if (moves.isEmpty()) {
            moves.add(getRandomMove(grid, width, height, rand));
        }
        // send response
        response.setMoves(moves.toArray(new Move[0]));
        return response;
    }

    protected static List<Move> applyAutomaticFlaggingAndReveal(char[][] grid, int width, int height) {
        List<Move> moves = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // get Cell at x,y
                Cell cell = new Cell(x,y,grid[x][y]);
                List<Cell> neighbours = getNeighbours(grid, x, y);
                if (cell.getNumber() > 0) {
                    int countFlags = (int) neighbours.stream().filter(c -> (c.isFlag())).count();
                    int countSpace = (int) neighbours.stream().filter(c -> (c.isSpace())).count();
                    LOG.log(Level.INFO,"Checking cell @{0},{1} with number {2} and {3} neighbours, {4} flags and {5} spaces",new Object[]{x,y,cell.getNumber(),neighbours.size(),countFlags,countSpace});
                    if ((cell.getNumber()==countFlags)&&(countSpace>0)) {
                        LOG.info("Placing spaces");
                        // mark all spaces
                        neighbours.forEach((c)->{if (c.isSpace()) {
                            moves.add(new Move(c.getX(),c.getY(),false));
                        }});
                    }
                    if ((cell.getNumber()==countFlags+countSpace)&&(countSpace>0)) {
                        LOG.info("Placing flags");
                        // mark all spaces
                        neighbours.forEach((c)->{if (c.isSpace()) {
                            moves.add(new Move(c.getX(),c.getY(),true));
                        }});
                    }
                }
            }
        }
        return moves;
    }

    private static List<Cell> getNeighbours(char[][] grid, int x, int y) {
        List<Cell> result = new ArrayList<>();
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                if ((dx != 0) || (dy != 0)) {
                    try {
                        Cell c = new Cell(x+dx,y+dy,grid[x + dx][y + dy]);
                        result.add(c);
                    } catch (IndexOutOfBoundsException e) {
                        // ignore
                    }
                }
            }
        }
        return result;
    }

    private Move getRandomMove(char[][] grid, int width, int height, Random rand) {
        Move move = null;
        while (move == null) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            char c = grid[x][y];
            if (c == '#') {
                move = new Move(x, y, false);
            }
        }
        return move;
    }
}