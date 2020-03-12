/** 
 */
package fr.houseofcode.dap.server.dma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Assert;
import org.junit.Test;

import fr.houseofcode.dap.server.dma.EmailController;

/**
 * @author dimam
 *
 */
public class EmailControllerIT {

	@Test
	public void testDisplayNbUnreadEmail() throws IOException, GeneralSecurityException {
		//Init Data
		EmailController ec = new EmailController();
		ec.setService(new GmailServiceImpl());

		//Appel de la méthode
		Integer result = ec.displaynbUnreadEmail("mamadou");

		//Contrôle de l'éxécution de la méthode
		Integer expectedNbEmail = 47;
		Assert.assertNotNull("NB d'email non présent", result);
		Assert.assertEquals(expectedNbEmail, result);
	}

}
