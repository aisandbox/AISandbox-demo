package dev.aisandbox.demo.bandit.random;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bandit/random")
public class BanditRandomUI {
  @Autowired
  Environment environment;

  @GetMapping("/index.html")
  public String index(Model model) {
    // work out server port and IP
    try {
      model.addAttribute("ip", InetAddress.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      model.addAttribute("ip", "localhost");
    }
    model.addAttribute("port",environment.getProperty("local.server.port"));
    return "banditRandom";
  }
}
