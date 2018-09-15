/**
 * This class provides a simple interface to perform text sentiment analysis
 * using Azure Cognitive Services.
 * For the functionality to work correctly, there must be a file called
 * AzureAccess.key with an access key for Azure Cognitive Services.
 */
// ** DO NOT CHANGE! **
package mp1;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class AzureSentimentAnalysis {

    private static String host = "";
    private static String path = "/text/analytics/v2.0/sentiment";
    private static String accessKey = "";

    /**
     * Initialize Azure Services using the provided access key and host settings,
     * which should be in the files AzureAccess.key and AzureAccess.hst,
     * respectively.
     *
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void init() throws IOException, FileNotFoundException {
        String key, host;
        try (BufferedReader br = new BufferedReader(new FileReader("AzureAccess.key"))) {
            key = br.readLine();
        }
        accessKey = key;
        try (BufferedReader br = new BufferedReader(new FileReader("AzureAccess.hst"))) {
            host = br.readLine();
        }
        AzureSentimentAnalysis.host = host;
    }

    /**
     * Set the Azure Services host
     * @param host
     */
    private static void setAzureHost(String host) {
        AzureSentimentAnalysis.host = host;
    }

    /**
     * Send a collection of text to Azure Cognitive Services for sentiment analysis.
     * @param textCollection for analysis
     * @return a list of SentimentResponses, one entry for each string/piece of text sent for analysis
     * @throws Exception
     */
    private static List<SentimentResponse> azureSndRcv(TextCollection textCollection) throws Exception {
        String text = new Gson().toJson(textCollection);
        byte[] encoded_text = text.getBytes("UTF-8");

        URL url = new URL(host + path);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/json");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(encoded_text, 0, encoded_text.length);

        wr.flush();
        wr.close();

        StringBuilder response = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return getSentiments(response.toString());
    }

    /**
     * Package JSON data received from Azure Cognitive Services into a
     * more usable list
     * @param json_text obtained from Azure Cognitive Services as a request
     *                  response
     * @return a list of SentimentResponses after parsing the JSON data
     */
    private static List<SentimentResponse> getSentiments(String json_text) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        List<SentimentResponse> responses = new LinkedList<>();
        Type collectionType = new TypeToken<List<SentimentResponse>>() {
            }.getType();
        responses = (new Gson()).fromJson(json.get("documents"), collectionType);
        return responses;
    }

    /**
     * Primary interface method for processing a sentiment analysis request
     *
     * @param textCollection is not null
     * @return the sentiment scores obtained from Azure Cognitive Services
     */
    public static List<SentimentResponse> getSentiments(TextCollection textCollection) {
        List<SentimentResponse> scores = new LinkedList<>();
        try {
            scores = azureSndRcv(textCollection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return scores;
    }

}
