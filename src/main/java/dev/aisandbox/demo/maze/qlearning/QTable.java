package dev.aisandbox.demo.maze.qlearning;

import dev.aisandbox.demo.SpriteLoader;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class QTable {

    private static final Logger LOG = Logger.getLogger(QTable.class.getName());

    private static final int SCALE = 25;
    @Getter
    String tableID = null;
    @Getter
    @Setter
    double learningValue = 0.8;
    @Getter
    @Setter
    double discount = 0.95;
    @Getter
    double startingValue = 0.0;
    @Getter
    int width = 0;
    @Getter
    int height = 0;
    Map<String, Map<String, Double>> tableRows = new TreeMap<>();
    List<String> actions = new ArrayList<>();

    @Autowired
    SpriteLoader spriteLoader;

    List<BufferedImage> sprites = null;

    public static String toStateString(int x, int y) {
        return "(" + x + "," + y + ")";
    }

    private static boolean isSameValues(Map<String, Double> map) {
        return map.values().stream().distinct().limit(2).count() <= 1;
    }

    /**
     * Get the maxim value of a row - ignoring values &lt; -500 or &gt; +500
     *
     * @param map
     * @return
     */
    private static double maxValue(Map<String, Double> map) {
        OptionalDouble max = map.values().stream().filter(v -> v > -500.0).filter(v -> v < 500.0).mapToDouble(v -> v).max();
        return max.isPresent() ? max.getAsDouble() : 0.0;
    }

    public Map<String, Double> getRow(String state) {
        return tableRows.get(state);
    }

    public void setStartingValue(double value) {
        startingValue = value;
        // update any existing states
        for (Map<String, Double> row : tableRows.values()) {
            for (String action : row.keySet()) {
                row.put(action, value);
            }
        }
    }

    // IMAGE PROCESSING METHODS

    public void reset(String tableID, int width, int height, String[] moves) {
        this.tableID = tableID;
        this.width = width;
        this.height = height;
        tableRows.clear();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String state = QTable.toStateString(x, y);
                Map<String, Double> actionMap = new TreeMap<>();
                for (String action : moves) {
                    actionMap.put(action, startingValue);
                }
                tableRows.put(state, actionMap);
            }
        }
    }

    public void learn(String initialState, String action, double reward, String endState) {
        Map<String, Double> oldRow = getRow(initialState);
        Map<String, Double> newRow = getRow(endState);
        double maxNewRow = newRow.values().stream().mapToDouble(value -> value).max().getAsDouble();
        oldRow.put(action, (1 - learningValue) * oldRow.get(action) + learningValue * (reward + discount * maxNewRow));
    }

    public String getBestAction(String state) {
        String bestMove = "North"; // default value - use if everything else fails
        Map<String, Double> row = tableRows.get(state);
        if (row != null) {
            double bestScore = Double.NEGATIVE_INFINITY;
            for (Map.Entry<String, Double> entry : row.entrySet()) {
                if (entry.getValue() > bestScore) {
                    bestMove = entry.getKey();
                    bestScore = entry.getValue();
                }
            }
        }
        return bestMove;
    }

    public int getImageWidth() {
        return SCALE * width;
    }

    public int getImageHeight() {
        return SCALE * height;
    }

    public BufferedImage getTableImage() {
        // load sprites
        if (sprites == null) {
            sprites = spriteLoader.loadSprites("/graphics/maze.png", 25, 25);
            LOG.log(Level.INFO, "Loaded {0} sprites", new Object[]{sprites.size()});
        }
        BufferedImage image = new BufferedImage(getImageWidth(), getImageHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        // work out the range of max values - but ignore anything <-500 or > +500
        double max = 0.0;
        double min = 0.0;
        for (Map<String, Double> row : tableRows.values()) {
            double bestValue = maxValue(row);
            if (bestValue > max) {
                max = bestValue;
            }
            if (bestValue < min) {
                min = bestValue;
            }
        }
        // check for max==min - this would cause a devide by zero error
        if (max==min) {
            max = min +1.0;
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String state = QTable.toStateString(x, y);
                Map<String, Double> row = tableRows.get(state);
                // work out background color
                Color background;
                if (isSameValues(row)) {
                    background = Color.LIGHT_GRAY;
                } else {
                    float n = (float)((maxValue(row)-min)/(max-min));
                    background = new Color(n,1.0f,1.0f);
                }
                g.setColor(background);
                g.fillRect(x * SCALE, y * SCALE, SCALE, SCALE);
                // work out which icon to use - assume < -500 = wall
                int icon = 0;
                if (row.get("North") < -500.0) {
                    icon += 1;
                }
                if (row.get("East") < -500.0) {
                    icon += 2;
                }
                if (row.get("South") < -500.0) {
                    icon += 4;
                }
                if (row.get("West") < -500.0) {
                    icon += 8;
                }
                g.drawImage(sprites.get(icon), x * SCALE, y * SCALE, null);

                switch (getBestAction(state)) {
                    case "North":
                        g.drawImage(sprites.get(16), x * SCALE, y * SCALE, null);
                        break;
                    case "East":
                        g.drawImage(sprites.get(17), x * SCALE, y * SCALE, null);
                        break;
                    case "South":
                        g.drawImage(sprites.get(18), x * SCALE, y * SCALE, null);
                        break;
                    case "West":
                        g.drawImage(sprites.get(19), x * SCALE, y * SCALE, null);
                        break;
                    default:
                        LOG.warning("Unexpected direction '"+getBestAction(state)+"'");
                }
            }
        }
        return image;
    }


}
