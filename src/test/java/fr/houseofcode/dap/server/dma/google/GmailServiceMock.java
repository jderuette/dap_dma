/**
 * 
 */
package fr.houseofcode.dap.server.dma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import fr.houseofcode.dap.server.dma.GmailService;

/**
 * @author dimam
 *
 */
public class GmailServiceMock implements GmailService {
	/**
	 * MOCK : renvoie le nombre d'emails non lus dans la boîte principale;
	 * @return The UnreadedMail of the user inbox
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@Override
	public Integer UnreadedMail(String Userkey) throws IOException, GeneralSecurityException {
		//LOG.info("Récupération du nombre d'email pour l'utilisateur " + Userkey);
		return 12;
	}

	@Override
	public String getLabels(String UserKey) throws IOException, GeneralSecurityException {
		return "bouch1, bouch2";
	}

}
