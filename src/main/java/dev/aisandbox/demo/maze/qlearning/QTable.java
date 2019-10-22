package dev.aisandbox.demo.maze.qlearning;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class QTable {

    @Getter
    @Setter
    String tableID = null;

    @Getter
    @Setter
    double learningValue = 0.8;

    @Getter
    @Setter
    double discount = 0.95;

    Map<String, Map<String, Double>> tableRows = new TreeMap<>();

    public Map<String, Double> getRow(String state) {
        return tableRows.get(state);
    }

    public void initialiseState(String state, String[] moves) {
        Map<String, Double> actions = new TreeMap<>();
        for (String action : moves) {
            actions.put(action, 0.0);
        }
        tableRows.put(state, actions);
    }

    public void reset() {
        tableRows.clear();
    }
}
