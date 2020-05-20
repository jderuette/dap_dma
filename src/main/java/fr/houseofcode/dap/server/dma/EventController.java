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

//TODO DMA by Djer |Audit Code| Prends en compte les remarques de CheckStyle !
//TODO DMA by Djer |JavaDoc| Il manque la description de la classe

/**
 * @author dimam
 *
 */
@RestController
public class EventController {
    @Autowired
    private Calendarservice service;

    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    @RequestMapping("/Calendar/Events")
    public String displayNextEvent(@RequestParam String UserKey) throws IOException, GeneralSecurityException {
        return service.NextEvent(UserKey);
    }

}
