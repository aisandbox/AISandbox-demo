package dev.aisandbox.demo.twisty.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/** TwistyRequest */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwistyRequest {
  private TwistyRequestHistory history = null;
  private String puzzleType = null;
  private List<String> moves = new ArrayList<>();
  private String state = null;
}
