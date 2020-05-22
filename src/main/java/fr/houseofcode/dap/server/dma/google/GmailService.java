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

//TODO DMA by Djer |Audit Code| Prends en compte les remarques de CheckStyle !
//TODO DMA by Djer |JavaDoc| Il manque la description de la classe

/**
 * @author adminHOC
 *
 */
@Service
public class GmailService {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final Logger LOG = LogManager.getLogger();

    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    /**
     * allow the secured acess to Gmail.
     * @return a GmailService instance with secured transport.
     * @throws IOException if the sent or received message is broken.
     * @throws GeneralSecurityException i there's a security failure.
     */
    private Gmail getService(String UserKey) throws IOException, GeneralSecurityException {
        //TODO DMA by Djer |Log4J| contextualise tesm essages de log "for userKey + " UserKey.
        LOG.debug("Connexion au service utilisateur de Google ... :  ");
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, Utils.getCredentials(HTTP_TRANSPORT, UserKey))
                .setApplicationName(APPLICATION_NAME).build();
        return service;
    }

    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    /**.
     * allow a secured acess to Gmail
     * @return The labels of the inbox user
     * @throws IOException ins an output or output exception for some instances.
     * @throws GeneralSecurityException is a generic security exception.
     */
    public String getLabels(String UserKey) throws IOException, GeneralSecurityException {
        // Print the labels in the user's account.
        //TODO DMA by Djer |IDE| Encodage : attention tes fichiers étaient encodés en ISO-8859-2 lorsque tu as ajouté ce messages !
        //TODO DMA by Djer |Log4J| Contectualise tes messages " ... for userKey : " + UserKey".
        LOG.debug("RÃ©cupÃ©ration des labels de l'utilisateur ... :  ");
        String user = "me";
        ListLabelsResponse listResponse = getService(UserKey).users().labels().list(user).execute();
        List<Label> labels = listResponse.getLabels();
        String message = " ";
        if (labels.isEmpty()) {
            //TODO DMA by Djer |IDE| Evite les multiple return dans une même méthode. Alimente ta variable "message" existante. Tes if/else évite déja d'éxécuter du code inutilement.
            return "Vous n'avez pas de label";
        } else {

            for (Label label : labels) {
                //TODO DMA by Djer |Rest API| Dans une API REST évite de formater les messages. Renvoie une Liste et laisse le client (ou thymeLeaf) effectuer la présentation.
                message = message + " \n " + label.getName();
            }
        }
        return message;
    }

    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    /**
     * @return The UnreadedMail of the user inbox
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public Integer UnreadedMail(String Userkey) throws IOException, GeneralSecurityException {
        //TODO DMA by Djer |IDE| Encodage : attention tes fichiers étaient encodés en ISO-8859-2 lorsque tu as ajouté ce message !
        //TODO DMA by Djer |Log4J| Contectualise tes messages " ... for userKey : " + UserKey".
        LOG.debug("AccÃ©s aux emails non lus de l'utilisateur ... :  ");
        Integer result = 0;
        String user = "me";
        ListMessagesResponse listMResponse = getService(Userkey).users().messages().list(user)
                .setQ("is:unread in:inbox -category:promotions -category:social").execute();
        List<Message> messages = listMResponse.getMessages();

        //TODO DMA by Djer |API Google| Il faut prendre en compte la pagination des résultats. Ici un utilisateur qui a plus de 100 email non lut verra affiché "100".
        if (messages != null) {
            if (!messages.isEmpty()) {
                result = messages.size();
            }
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
        //TODO DMA by Djer |IDE| Encodage : attention tes fichiers étaient encodés en ISO-8859-2 lorsque tu as ajouté ce message !
        //TODO DMA by Djer |Log4J| Contectualise tes messages " ... for userKey : " + UserKey".
        LOG.debug("RÃ©cupÃ©ration de la liste des messages de l'utilisateur ... :  ");
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
            //TODO DMA by Djer |POO| Pas de SysOut sur un serveur ! (soit une log.debug, soit supprimer cette ligne)
            System.out.println(message.toPrettyString());
        }

        return messages;
    }
}