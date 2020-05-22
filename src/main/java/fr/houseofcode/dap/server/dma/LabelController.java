/** 
 */
package fr.houseofcode.dap.server.dma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.dma.google.GmailServiceImpl;

//TODO DMA by Djer |Audit Code| Prends en compte les remarques de CheckStyle !
//TODO DMA by Djer |JavaDoc| Il manque la description de la classe

/**
 * @author dimam
 *
 */
@RestController
public class LabelController {
    @Autowired
    private GmailServiceImpl service;

    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    @RequestMapping("/email/Labels")
    public String displaygetLabels(String UserKey) throws IOException, GeneralSecurityException {
        return service.getLabels(UserKey);
    }

}
