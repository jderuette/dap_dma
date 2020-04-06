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

import fr.houseofcode.dap.server.dma.google.GmailService;

//TODO DMA by Djer |Audit Code| Prends en compte les remarques de CheckStyle !

/**
 * @author dimam
 *
 */
@RestController
public class EmailController {
    @Autowired
    private GmailService service;

    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    @RequestMapping
    public Integer displaynbUnreadEmail(@RequestParam String UserKey) throws IOException, GeneralSecurityException {
        return service.UnreadedMail(UserKey);

    }
}
