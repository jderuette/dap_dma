/** 
 */
package fr.houseofcode.dap.server.dma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.houseofcode.dap.server.dma.google.GmailServiceImpl;

/**
 * @author dimam
 *
 */

@RestController
public class LabelController {
    @Autowired
    private GmailServiceImpl service;

    @RequestMapping("/email/Labels")
    public String displaygetLabels(String UserKey) throws IOException, GeneralSecurityException {
        return service.getLabels(UserKey);
    }

}
