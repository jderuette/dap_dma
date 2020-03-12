/**

 */
package fr.houseofcode.dap.server.dma;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dimam
 *
 */
@RestController
public class EmailController {
	@Autowired
	private GmailService service;
	private static final Logger LOG = LogManager.getLogger();

	@RequestMapping
	public Integer displaynbUnreadEmail(@RequestParam String UserKey) throws IOException, GeneralSecurityException {
		LOG.info("Affichage du nombre d'email pour l'utilisateur : " + UserKey);
		return service.UnreadedMail(UserKey);

	}

	public void setService(final GmailService gmailService) {
		this.service = gmailService;

	}
}
