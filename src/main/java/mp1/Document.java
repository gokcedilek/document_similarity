package mp1;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Collections;
import java.util.Scanner;

public class Document implements Comparable<Document> {
    private final String docURL;                 // the URL for the document
    private final String id;                     // an identifier for the document, e.g., book title

    // You may need more fields for a document.

    /**
     * Create a document given a URL to the document's text as well as an id
     *
     * @param id is not null and is not the empty
     * @param url is not null and is not the empty string
     * @throws IOException
     */
    public Document(String id, String url) throws IOException {
        assert (id != null);
        assert (!id.equals(""));
        assert (url != null);
        assert (!url.equals(""));

        this.docURL = url;
        this.id = id;

        // TODO: Your constructor will have more work to do.

    }

    public Document(String url) throws IOException {
        // a simpler constructor that sets the id to be the url
        this(url, url);
    }

    /**
     * Compute the Jensen-Shannon Divergence between the two textList
     * on the basis of the words used.
     *
     * @param otherDoc is not null
     * @return the Jensen-Shannon divergence multiplied by 100 and rounded to the nearest integer
     */
    public long computeJSDiv(Document otherDoc) {
        // TODO: Implement this method
        return -1;
    }

    /**
     * Compute the overall sentiment of the Document.
     * The overall sentiment is the median sentiment obtained by computing
     * the median of segments of length of ~5000 characters.
     * @return the overall sentiment (multiplied by 100 and rounded to the nearest integer)
     */
    public int getOverallSentiment() {
        // TODO: Implement this method
        return -1;
    }

    /**
     * Produce a string representation of a Document
     */
    @Override
    public String toString() {
        // return the id associated with this document
        return this.id;
    }


    // There should be no need to change any of the code below.
    // Read, but do not change.

    /**
     * Compare two Document objects for equality
     *
     * @param other
     * @return true if this Document and the other Document represent the same
     * document.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Document) {
            Document otherDoc = (Document) other;
            return (this.id.equals(otherDoc.id));
        } else {
            return false;
        }
    }

    /**
     * Compute the hashCode for this Document object
     *
     * @return the hashCode for this Document object
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public int compareTo(Document other) {
        if (this.equals(other)) {
            return 0;
        } else {
            return id.compareTo(other.id);
        }
    }

}
