package dev.aisandbox.demo.maze.api;

import lombok.Data;

/**
 * <p>Config class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class Config {
    private String boardID;
    private String[] validMoves;
    private int width;
    private int height;
}
