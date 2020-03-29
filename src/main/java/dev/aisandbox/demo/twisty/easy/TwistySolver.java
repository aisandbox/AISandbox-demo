package dev.aisandbox.demo.twisty.easy;

import java.util.List;

public interface TwistySolver {
    public String getPuzzleType();
    public String getAlgorithmName();
    public String getMoves(String puzzleType,String state, List<String> availableMoves) throws SolverException;
}
