package dev.aisandbox.demo.mine.api;

import lombok.Data;

@Data
public class MineRequest {
    String boardID;
    String[] board;
    int flagsRemaining;
}
