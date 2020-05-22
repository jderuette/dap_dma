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

//TODO DMA by Djer |Audit Code| Prends en compte les remarques de CheckStyle !
//TODO DMA by Djer |JavaDoc| Il manque la description de la classe

public class Utils {
    //TODO DMA by Djer |POO| Utilise le "System.getProperty("path.separator")" pour identifier les séparateurs de fichier de façon indépendante du Systeme d'Exploitation.
    private static final String TOKENS_DIRECTORY_PATH = System.getProperty("user.home") + "\\dap\\tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = new ArrayList<String>();

    //private static final String CREDENTIALS_FILE_PATH = System.getProperty("user.home") + "\\dap\\credentials.json";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    //TODO DMA by Djer |POO| Les paramètres doivent commencer par une minuscule puis sont en CamelCase.
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

    //TODO DMA by Djer |POO| Les constantes doivent être entièrement en majuscule (avec les "espace" avec des underscore _)
    //TODO DMA by Djer |POO| Les Constante sont au début de la classe (ordre : constantes, attributs, initialisateur statics, constructeurs, méthodes "métier", méthodes "utilitaires", getters/setters)
    public static String Separator = FileSystems.getDefault().getSeparator();

    public static GoogleAuthorizationCodeFlow getFlow(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        //TODO DMA by Djer |IDE| Supprime les TO-DO une fois que tu les as traités.
        // TODO Auto-generated method stub
        // Build flow and trigger user authorization request.
        // Load client secrets.
        //TODO DMA by Djer |POO| Evite d'ajouter les scopes ici, il seront duppliqués à chaque passage dans la méthode. Créer un constructeur pour que ce code ne soit éxécuté qu'une seul fois.
        SCOPES.add(CalendarScopes.CALENDAR_READONLY);
        SCOPES.add(GmailScopes.GMAIL_READONLY);

        // InputStream in = Calendarservice.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        //if (in == null) {
        //   throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        // }

        //TODO DMA by Djer |POO| Devrait être une constante de la classe.
        //TODO DMA by Djer |POO| Utilise ta vairaible "Separator" pour identifier les séparateurs de fichier y compris entre les dossiers.
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
