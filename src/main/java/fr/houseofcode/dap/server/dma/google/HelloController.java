package fr.houseofcode.dap.server.dma.google;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mams
 *
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return " Yo tout le monde ^^ \n Bienvenue à tous >-<' ";
    }
}
