package dev.aisandbox.demo.bandit.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/** BanditResponse */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BanditResponse {
  private int arm;
}
