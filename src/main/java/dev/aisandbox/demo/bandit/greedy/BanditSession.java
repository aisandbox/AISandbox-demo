package dev.aisandbox.demo.bandit.greedy;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BanditSession {

  @Getter private final String sessionID;
  private final int bandits;
  @Getter private double[] averageReward;
  private int[] pulls;
  @Getter private Set<Integer> unpulled = new HashSet<>();

  public BanditSession(String sessionID, int bandits) {
    this.sessionID = sessionID;
    this.bandits = bandits;
    averageReward = new double[bandits];
    pulls = new int[bandits];
    // make a note of all unpulled bandits
    for (int i = 0; i < bandits; i++) {
      unpulled.add(i);
    }
  }

  public void recordReward(int bandit, double reward) {
    // note that we've pulled this bandit
    unpulled.remove(bandit);
    // increase the number of pulls for this bandit
    pulls[bandit]++;
    // recalc the average
    averageReward[bandit] =
        averageReward[bandit] + (reward - averageReward[bandit]) / (pulls[bandit]);
  }

  public int getBestBandit() {
    double bestMean = Double.NEGATIVE_INFINITY;
    int argMax = 0;
    for (int i = 0; i < averageReward.length; i++) {
      if (averageReward[i] > bestMean) {
        argMax = i;
        bestMean = averageReward[i];
      }
    }
    return argMax;
  }
}
