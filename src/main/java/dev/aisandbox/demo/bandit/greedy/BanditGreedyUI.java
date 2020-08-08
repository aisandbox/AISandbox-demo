package dev.aisandbox.demo.bandit.greedy;

import dev.aisandbox.demo.NetworkInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bandit/greedy")
public class BanditGreedyUI {

  private final NetworkInformation network;

  @Autowired
  public BanditGreedyUI(NetworkInformation network) {
    this.network = network;
  }

  @GetMapping("/index.html")
  public String index(Model model) {
    // work out server port and IP
    model.addAttribute("ip", network.getServerAddress());
    model.addAttribute("port", network.getServerPort());
    return "banditGreedy";
  }
}
