package dev.aisandbox.demo.mine.rules;

import dev.aisandbox.demo.mine.api.Move;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RuleTests {

    private char[][] makeGrid(String[] lines) {
        char[][] grid = new char[lines[0].length()][lines.length];
        for (int y=0;y<lines[0].length();y++) {
            String line = lines[y];
            for (int x=0;x<line.length();x++) {
                grid[x][y] = line.charAt(x);
            }
        }
        return grid;
    }


    @Test
    public void rule1FlagTest() {
        char[][] grid = makeGrid(new String[]{"...",".12",".2#"});
        List<Move> moves = MineRulesAPI.applyAutomaticFlaggingAndReveal(grid,3,3);
        assertEquals("Move count",1,moves.size());

    }
}
