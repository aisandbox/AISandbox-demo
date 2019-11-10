package dev.aisandbox.demo.maze.qlearning;

import dev.aisandbox.demo.maze.api.MazeRequest;
import dev.aisandbox.demo.maze.api.MazeResponse;
import dev.aisandbox.demo.maze.api.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class MazeQLearningAPI {

    private static final Logger LOG = Logger.getLogger(MazeQLearningAPI.class.getName());

    // Storage for all of the QTables
    @Autowired
    QTable qtable;

    private static String toStateString(Position p) {
        return QTable.toStateString(p.getX(), p.getY());
    }

    @PostMapping(path = "/ai/maze/q", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MazeResponse getQLearningResponse(@RequestBody MazeRequest req) {
        // check we're looking at an existing table
        if (!req.getConfig().getBoardID().equals(qtable.getTableID())) {
            LOG.info("Creating new table for board "+req.getConfig().getBoardID());
            // this is a new table - initialise it
            qtable.reset(
                    req.getConfig().getBoardID(),
                    req.getConfig().getWidth(),
                    req.getConfig().getHeight(),
                    req.getConfig().getValidMoves());
        }

        // learn from history
        if (req.getHistory() != null) {
            qtable.learn(
                    toStateString(req.getHistory().getLastPosition()),
                    req.getHistory().getAction(),
                    req.getHistory().getReward(),
                    toStateString(req.getHistory().getNewPosition()));
        }

        // get the next move
        MazeResponse response = new MazeResponse();
        response.setMove(
                qtable.getBestAction(
                        QTable.toStateString(
                                req.getCurrentPosition().getX(),
                                req.getCurrentPosition().getY()))
        );
        return response;
    }

}
