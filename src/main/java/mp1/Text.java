package mp1;

// This class is used for sending text to Azure Cognitive Services
// ** DO NOT CHANGE **

import java.util.ArrayList;
import java.util.List;

/**
 * A type that represents a single request to Azure Cognitive Services
 */
public class Text {
    private String id, language, text;

    public Text(String id, String language, String text) {
        this.id = id;
        this.language = language;
        this.text = text;
    }
}

/**
 * A type that collects a list of requests to send to Azure Cognitive Services.
 * Azure Cognitive Services can process at most 100 strings, each of length at
 * most 5000 in a single call.
 */
class TextCollection {
    private List<Text> documents;
    private static final int MAX_RQ_LENGTH = 5000;
    private static final int MAX_REQUESTS = 100;

    /**
     * Default constructor for TextCollection
     */
    public TextCollection() {
        this.documents = new ArrayList<Text>();
    }

    /**
     * Return the current size of the TextCollection
     * @return the current size of the TextCollection
     */
    public int size() {
        return documents.size();
    }

    /**
     * Add a new text entry for analysis to the collection. If the collection
     * contains the maximum permitted number of requests, additional text is ignored.
     *
     * @param id is not null
     * @param language is either "en" or "es"
     * @param text is the string to analyse for sentiment, and
     *             its length should be <= 5000, and longer strings are trimmed
     *             to 5000 characters.
     */
    public void add(String id, String language, String text) {
        if (documents.size() < MAX_REQUESTS) {
            if (text.length() > MAX_RQ_LENGTH) {
                text = text.substring(0, MAX_RQ_LENGTH);
            }
            this.documents.add(new Text(id, language, text));
        }
    }
}
