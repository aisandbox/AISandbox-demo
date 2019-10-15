package dev.aisandbox.demo.maze.qlearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/maze")
public class MazeQLearningUI {

    private static final Logger LOG = Logger.getLogger(MazeQLearningUI.class.getName());

    @Autowired
    QTable memory;

    @GetMapping("/index.html")
    public String index(Model model) {
        return "mazeIndex";
    }

}
