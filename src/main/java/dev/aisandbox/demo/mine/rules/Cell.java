package dev.aisandbox.demo.mine.rules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Cell {
    @Getter
    private final int x;
    @Getter
    private final int y;
    private final char c;

    public int getNumber() {
        return "0123456789".indexOf(c);
    }

    public boolean isFlag() {
        return c=='F';
    }

    public boolean isSpace() {
        return c=='#';
    }
}
