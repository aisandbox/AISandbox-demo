package dev.aisandbox.demo.mine.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Move {
    int x;
    int y;
    boolean flag;
}
