package dev.aisandbox.demo.maze.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * <p>MazeRequest class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MazeRequest {

    private Config config;

    private History history;

    private Position currentPosition;

}
