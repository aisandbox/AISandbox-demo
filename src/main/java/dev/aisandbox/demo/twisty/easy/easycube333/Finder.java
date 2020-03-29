package dev.aisandbox.demo.twisty.easy.easycube333;

public class Finder {

  public static Position findEdge(String state, char prime, char second) {
    assert (state.indexOf('x') == -1);
    assert (state.indexOf('y') == -1);
    String target = state.replace(prime, 'x').replace(second, 'y');
    if (target.matches(".......x...........y.................................."))
      return Position.EDGE_TOP_FRONT;
    if (target.matches(".....x......................y........................."))
      return Position.EDGE_TOP_RIGHT;
    if (target.matches(".x...................................y................"))
      return Position.EDGE_TOP_BACK;
    if (target.matches("...x......y..........................................."))
      return Position.EDGE_TOP_LEFT;
      if (target.matches(".......y...........X.................................."))
        return Position.EDGE_FRONT_TOP;
    if (target.matches(".......................x......y......................."))
      return Position.EDGE_FRONT_RIGHT;
    if (target.matches(".........................x....................y......."))
      return Position.EDGE_FRONT_BOTTOM;
    if (target.matches("..............y......x................................"))
      return Position.EDGE_FRONT_LEFT;
    if (target.matches(".....y......................x........................."))
      return Position.EDGE_RIGHT_TOP;
    if (target.matches("................................x......y.............."))
      return Position.EDGE_RIGHT_BACK;
    if (target.matches("..................................x...............y..."))
      return Position.EDGE_RIGHT_BOTTOM;
    if (target.matches(".......................y......x......................."))
      return Position.EDGE_RIGHT_FRONT;
    if (target.matches("...y......x..........................................."))
      return Position.EDGE_LEFT_TOP;
    if (target.matches("..............x......y................................"))
      return Position.EDGE_LEFT_FRONT;
    if (target.matches("................x...............................y....."))
      return Position.EDGE_LEFT_BOTTOM;
    if (target.matches("............x............................y............"))
      return Position.EDGE_LEFT_BACK;
    if (target.matches(".........................y....................x......."))
      return Position.EDGE_BOTTOM_FRONT;
    if (target.matches("..................................y...............x..."))
      return Position.EDGE_BOTTOM_RIGHT;
    if (target.matches("...........................................y........x."))
      return Position.EDGE_BOTTOM_BACK;
    if (target.matches("................y...............................x....."))
      return Position.EDGE_BOTTOM_LEFT;
    if (target.matches(".y...................................x................"))
      return Position.EDGE_BACK_TOP;
    if (target.matches("............y............................x............"))
      return Position.EDGE_BACK_LEFT;
    if (target.matches("...........................................x........y."))
      return Position.EDGE_BACK_BOTTOM;
    if (target.matches("................................y......x.............."))
      return Position.EDGE_BACK_RIGHT;
    return null;
  }
}
