package dev.aisandbox.demo.maze.qlearning;

import dev.aisandbox.demo.maze.api.MazeRequest;
import dev.aisandbox.demo.maze.api.MazeResponse;
import dev.aisandbox.demo.maze.api.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MazeQLearningAPI {

    private static final Logger LOG = Logger.getLogger(MazeQLearningAPI.class.getName());

    private static final double LEARN_RATE = 1.0;
    private static final double DISCOUNT_FACTOR = 0.95;

    // Storage for all of the QTables
    @Autowired
    QTable qtable;

    @PostMapping(path = "/ai/maze/q", produces = {MediaType.APPLICATION_JSON_VALUE})
    public MazeResponse getQLearningResponse(@RequestBody MazeRequest req) {
        // check we're looking at the correct table
        if (!req.getConfig().getBoardID().equals(qtable.getTableID())) {
            // this is a new table - initialise it
            qtable.setTableID(req.getConfig().getBoardID());
            qtable.reset();
            for (int x=0;x<req.getConfig().getWidth();x++) {
                for (int y=0;y<req.getConfig().getHeight();y++) {
                    String state = toStateString(x,y);
                    qtable.initialiseState(state,req.getConfig().getValidMoves());
                }
            }
        }
        // learn from history
        if (req.getHistory()!=null) {
            String oldState = toStateString(req.getHistory().getLastPosition());
            String newState = toStateString(req.getHistory().getNewPosition());
            String oldAction = req.getHistory().getAction();
            double reward = req.getHistory().getReward();

            Map<String,Double> oldRow = qtable.getRow(oldState);
            Map<String,Double> newRow = qtable.getRow(newState);
            double maxNewRow = newRow.values().stream().mapToDouble(value -> value).max().getAsDouble();
            oldRow.put(oldAction,(1-LEARN_RATE)*oldRow.get(oldAction)+LEARN_RATE*(reward+DISCOUNT_FACTOR*maxNewRow));
        }

        // get the current row
        String currentState = toStateString(
                req.getCurrentPosition().getX(),
                req.getCurrentPosition().getY());
        Map<String,Double> qtableRow = qtable.getRow(currentState);
        LOG.info("Got current state "+qtableRow);
        /* choose the move with the highest value
        // NOTE: in the event of a tie, the first value read will be used. This is a bias that
        // could be removed by picking a random move from the ones tied.
         */
        String bestMove = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        for(Map.Entry<String,Double> entry : qtableRow.entrySet()) {
            if (entry.getValue() > bestScore) {
                bestMove = entry.getKey();
                bestScore = entry.getValue();
            }
        }
        MazeResponse response = new MazeResponse();
        response.setMove(bestMove);
        return response;
    }


    private QTable createQTable(int width,int height,String[] validMoves) {
        LOG.log(Level.INFO,"Generating new QTable with dimensions ({0},{1}) and moves {2}",new Object[] {width,height,validMoves});
        QTable qTable = new QTable();
        // generate an initial row for each possible state
        for (int x=0;x<width;x++) {
            for (int y=0;y<height;y++) {
                String state = toStateString(x,y);
                qTable.initialiseState(state,validMoves);
            }
        }
        return qTable;
    }

    private static String toStateString(int x,int y) {
        return "("+x+","+y+")";
    }

    private static String toStateString(Position p) {
        return toStateString(p.getX(),p.getY());
    }

}
