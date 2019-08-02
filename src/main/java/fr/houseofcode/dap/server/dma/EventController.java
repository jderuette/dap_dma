/**
 * 
 */
package fr.houseofcode.dap.server.dma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.dma.google.Calendarservice;

/**
 * @author dimam
 *
 */
@RestController
public class EventController {
    @Autowired
    private Calendarservice service;

    @RequestMapping("/Calendar/Events")
    public String displayNextEvent(@RequestParam String UserKey) throws IOException, GeneralSecurityException {
        return service.NextEvent(UserKey);
    }

}
