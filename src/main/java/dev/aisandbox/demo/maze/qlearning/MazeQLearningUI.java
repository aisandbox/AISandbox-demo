package dev.aisandbox.demo.maze.qlearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/maze/q")
public class MazeQLearningUI {

    private static final Logger LOG = Logger.getLogger(MazeQLearningUI.class.getName());
    @Autowired
    Environment environment;
    @Autowired
    QTable memory;

    @GetMapping("/index.html")
    public String index(Model model) {
        // work out server port and IP
        try {
            model.addAttribute("ip", InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            model.addAttribute("ip", "localhost");
        }
        model.addAttribute("port",environment.getProperty("local.server.port"));
        model.addAttribute("qtable",memory);
        return "mazeQLearning";
    }

    @GetMapping("/image.png")
    public void drawImage(HttpServletResponse response) throws IOException {
        BufferedImage image = memory.getTableImage();
        response.setContentType("image/png");
        ImageIO.write(image,"png",response.getOutputStream());
    }

}
