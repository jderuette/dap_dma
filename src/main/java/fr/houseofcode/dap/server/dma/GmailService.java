/**
 * 
 */
package fr.houseofcode.dap.server.dma;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

	String getLabels(String UserKey) throws IOException, GeneralSecurityException;

}
