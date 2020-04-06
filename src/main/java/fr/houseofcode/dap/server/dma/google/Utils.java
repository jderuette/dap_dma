package fr.houseofcode.dap.server.dma.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

public class Utils {

    private static final String TOKENS_DIRECTORY_PATH = System.getProperty("user.home") + "\\dap\\tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = new ArrayList<String>();
    //private static final String CREDENTIALS_FILE_PATH = System.getProperty("user.home") + "\\dap\\credentials.json";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
    * Creates an authorized Credential object.
    * @param HTTP_TRANSPORT The network HTTP Transport.
    * @return An authorized Credential object.
    * @throws IOException If the credentials.json file cannot be found.
    */

    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String UserKey) throws IOException {
        // LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        //return new AuthorizationCodeInstalledApp(getFlow(), receiver).authorize("user");
        GoogleAuthorizationCodeFlow flow = getFlow(HTTP_TRANSPORT);
        return flow.loadCredential(UserKey);
    }

    public static String Separator = FileSystems.getDefault().getSeparator();

    public static GoogleAuthorizationCodeFlow getFlow(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // TODO Auto-generated method stub
        // Build flow and trigger user authorization request.
        // Load client secrets.
        SCOPES.add(CalendarScopes.CALENDAR_READONLY);
        SCOPES.add(GmailScopes.GMAIL_READONLY);

        // InputStream in = Calendarservice.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        //if (in == null) {
        //   throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        // }

        File clientSecretsFic = new File(System.getProperty("user.home") + "\\dap\\credentials.json" + Separator);

        Reader read = new InputStreamReader(new FileInputStream(clientSecretsFic), Charset.forName("UTF-8"));

        //InputStream ir = GoogleClientSecrets.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, read);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline").build();
        return flow;

    }
}
