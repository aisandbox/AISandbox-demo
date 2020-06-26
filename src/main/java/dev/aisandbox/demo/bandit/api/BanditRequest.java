package dev.aisandbox.demo.bandit.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** BanditRequest */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BanditRequest {
  private BanditRequestHistory history = null;
  private String sessionID;
  private int banditCount;
  private int pullCount;
  private int pull;
}
