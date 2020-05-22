/**
 * 
 */
package fr.houseofcode.dap.server.dma;

import java.io.IOException;
import java.security.GeneralSecurityException;

//TODO DMA by Djer |Audit Code| Prends en compte les remarques de CheckStyle !
//TODO DMA by Djer |JavaDoc| Il manque la description de la classe
/**
 * @author dimam
 *
 */
public interface GmailService {
    /**
     * @return The UnreadedMail of the user inbox
     * @throws IOException
     * @throws GeneralSecurityException
     */
    Integer UnreadedMail(String Userkey) throws IOException, GeneralSecurityException;

    //TODO DMA by Djer |JavaDoc| Il manque la description de la méthode, de la valeur de retour et la description du paramètre
    String getLabels(String UserKey) throws IOException, GeneralSecurityException;

}
