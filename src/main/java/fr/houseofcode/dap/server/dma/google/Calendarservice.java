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
    @RequestMapping("/prochainevent")
    public String NextEvent(String UserKey) throws IOException, GeneralSecurityException {
        String allEvents = "";
        // Build a new authorized API client service.
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
                EventDateTime start = event.getStart().setDateTime(endDateTime);
                if (start == null) {
                    start = event.getStart().setDateTime(endDateTime);

                }
                allEvents = allEvents + " -  " + event.getSummary() + "( " + start + ")\n";

            }
        }
        return allEvents;
    }
}
