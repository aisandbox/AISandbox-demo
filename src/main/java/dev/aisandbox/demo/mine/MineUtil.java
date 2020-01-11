package dev.aisandbox.demo.mine;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MineUtil {
    public static char[][] readMineGrid(String[] rows) {
        char[][] grid = new char[rows[0].length()][rows.length];
        for (int y=0;y<rows.length;y++) {
            for (int x=0;x<rows[y].length();x++) {
                grid[x][y] = rows[y].charAt(x);
            }
        }
        return grid;
    }
}
