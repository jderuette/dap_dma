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
//TODO DMA by Djer |JavaDoc| Il manque la description de la classe

/**
 * @author dimam
 *
 */
@RestController
public class EmailController {
    @Autowired
    private GmailService service;

    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    //TODO DMA by Djer |Spring| Il devrait y avoir en paramètre de l'annotation, l'URL exposé pour acceder à cette méthode. Comme il n'y a qu'une seul méthode, Spring l'expose par defaut via le nom du controller mais ca n'est pas top.
    @RequestMapping
    public Integer displaynbUnreadEmail(@RequestParam String UserKey) throws IOException, GeneralSecurityException {
        return service.UnreadedMail(UserKey);

    }
}
