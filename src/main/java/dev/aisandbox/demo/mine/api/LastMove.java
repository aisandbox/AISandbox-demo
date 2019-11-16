package dev.aisandbox.demo.mine.api;

import lombok.Data;

@Data
public class LastMove {
    String boardID;
    Result result;

    public enum Result {won,lost,continued}
}
