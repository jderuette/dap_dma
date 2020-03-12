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
public class EmailControllerTest {

	@Test
	public void testDisplayNbUnreadEmail() throws IOException, GeneralSecurityException {
		//Init Data
		EmailController ec = new EmailController();
		ec.setService(new GmailServiceMock());

		//Appel de la méthode
		Integer result = ec.displaynbUnreadEmail("mamadou");

		//Contrôle de l'éxécution de la méthode
		Integer expectedNbEmail = 12;
		Assert.assertNotNull("NB d'email non présent", result);
		Assert.assertEquals(expectedNbEmail, result);
	}

}
