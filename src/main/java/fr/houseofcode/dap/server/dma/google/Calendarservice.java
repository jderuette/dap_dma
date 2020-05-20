package fr.houseofcode.dap.server.dma.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

//TODO DMA by Djer |Audit Code| Prends en compte les remarques de CheckStyle !
//TODO DMA by Djer |JavaDoc| Il manque la description de la classe
//TODO DMA by Djer |POO| LE nom de la classe doit etre ne CamelCase "Service" doit donc commencer par une majuscule.

/**
 * @author adminHOC
 *
 */
@RestController
public class Calendarservice {

    private static final Logger LOG = LogManager.getLogger();
    /** the internal application name. **/
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    /**  Json interpreter. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
    * @return All the events of the calendar
    * @throws IOException
    * @throws GeneralSecurityException
    */
    //TODO DMA by Djer |Rest API| Nom de route pas top, "event/next" serait mieux.
    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule.
    //TODO DMA by Djer |API Google| Tu ne devrais afifché que **un seul** next Event
    @RequestMapping("/prochainevent")
    public String NextEvent(String UserKey) throws IOException, GeneralSecurityException {
        String allEvents = "";
        // Build a new authorized API client service.
        //TODO DMA by Djer |POO| Seul les constantes (static ET final) doivent être écrites en majuscules.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                Utils.getCredentials(HTTP_TRANSPORT, UserKey)).setApplicationName(APPLICATION_NAME).build();

        // List the next 10 events from the primary calendar.

        DateTime now = new DateTime(System.currentTimeMillis());
        DateTime endDateTime = new DateTime("2015-05-28T17:00:00-07:00");
        Events events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            allEvents = "Pas de prochains evenements.";
        } else {
            for (Event event : items) {
                //TODO DMA by Djer |API Google| Pourquoi modifier la date de début qui provient de Google par une date "bidon" ? 
                EventDateTime start = event.getStart().setDateTime(endDateTime);
                if (start == null) {
                    //TODO DMA by Djer |API Google| Pourquoi modifier la date de début qui provient de Google par une date "bidon" ?
                    start = event.getStart().setDateTime(endDateTime);

                }
                //TODO DMA by Djer |Rest API| Dans une API évite de formater les messages. Renvoie une Liste et laisse le client (ou thymeLeaf) effectuer la présentation.
                allEvents = allEvents + " -  " + event.getSummary() + "( " + start + ")\n";

            }
        }
        return allEvents;
    }
}
