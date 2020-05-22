package fr.houseofcode.dap.server.dma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import fr.houseofcode.dap.server.dma.GmailService;

/**
 * @author adminHOC
 *
 */
@Service
public class GmailServiceImpl implements GmailService {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final Logger LOG = LogManager.getLogger();

    /**
     * allow the secured acess to Gmail.
     * @return a GmailService instance with secured transport.
     * @throws IOException if the sent or received message is broken.
     * @throws GeneralSecurityException i there's a security failure.
     */
    private Gmail getService(String UserKey) throws IOException, GeneralSecurityException {
        //TODO DMA by Djer |Log4J| Contextualise tes messages " ... for userKey : " + UserKey".
        LOG.debug("Connexion au service utilisateur de Google ... :  ");
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, Utils.getCredentials(HTTP_TRANSPORT, UserKey))
                .setApplicationName(APPLICATION_NAME).build();
        return service;
    }

    /**.
     * allow a secured acess to Gmail
     * @return The labels of the inbox user
     * @throws IOException ins an output or output exception for some instances.
     * @throws GeneralSecurityException is a generic security exception.
     */
    @Override
    public String getLabels(String UserKey) throws IOException, GeneralSecurityException {
        //TODO DMA by Djer |POO| Ce commentaire est devenu faux
        // Print the labels in the user's account.
        //TODO DMA by Djer |Log4J| Contextualise tes messages " ... for userKey : " + UserKey".
        LOG.debug("R√©cup√©ration des labels de l'utilisateur ... :  ");
        String user = "me";
        ListLabelsResponse listResponse = getService(UserKey).users().labels().list(user).execute();
        List<Label> labels = listResponse.getLabels();
        String message = " ";
        if (labels.isEmpty()) {
            return "Vous n'avez aucun label.";
        } else {

            for (Label label : labels) {
                message = message + " \n " + label.getName();
            }
        }
        return message;
    }

    /**
     * @return The UnreadedMail of the user inbox
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @Override
    public Integer UnreadedMail(String Userkey) throws IOException, GeneralSecurityException {
        LOG.info("R√©cup√©ration du nombre d'email pour l'utilisateur " + Userkey);

        //TODO DMA by Djer |Log4J|  Cette Log est redondante avec celle du dessus. Quelqu'un lisant les logs "debug" vera forcÈment les logs Info car le niveau "info" est plus ÈlÈvÈ que "debug".
        LOG.debug("Acc√©s aux emails non lus de l'utilisateur ... :  ");
        Integer result = 0;
        String user = "me";
        ListMessagesResponse listMResponse = getService(Userkey).users().messages().list(user)
                .setQ("is:unread in:inbox -category:promotions -category:social").execute();

        List<Message> messages = listMResponse.getMessages();

        if (messages != null) {
            if (!messages.isEmpty()) {
                result = messages.size();
            }
            //TODO DMA by Djer |Log4J| Contextualise tes messages " ... for userKey : " + UserKey".
            LOG.info("Nombre de messages : " + messages.size());
        }
        return result;
    }

    /**
       * List all Messages of the user's mailbox matching the query.
       *
       * @param service Authorized Gmail API instance.
       * @param userId User's email address. The special value "me"
       * can be used to indicate the authenticated user.
       * @param query String used to filter the Messages listed.
       * @throws IOException
       */
    public static List<Message> listMessagesMatchingQuery(Gmail service, String userId, String query)
            throws IOException {
        //TODO DMA by Djer |Log4J| Contextualise tes messages " ... for userKey : " + userId".
        LOG.debug("R√©cup√©ration de la liste des messages de l'utilisateur ... :  ");
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setQ(query).setPageToken(pageToken).execute();
            } else {
                break;
            }
        }

        for (Message message : messages) {
            //TODO DMA by Djer |Log4J| Pas de SysOut sur un serveur ! Eventuellement une log (en debug ?)
            System.out.println(message.toPrettyString());
        }

        return messages;
    }
}