package mp1;

/**
 * This data type holds a Document tuple and includes methods for
 * working with the tuple.
 */

// ** DO NOT CHANGE! **

public class DocumentPair implements Comparable<DocumentPair> {

    private Document doc1, doc2;
    private int jsDiv;
    private int sentimentDiff;

    /**
     * Create a new Document pair given two Documents
     *
     * @param doc1 not null
     * @param doc2 not null
     */
    public DocumentPair(Document doc1, Document doc2) {
        assert ((doc1 != null) && (doc2 != null));
        this.doc1 = doc1;
        this.doc2 = doc2;
        jsDiv = (int) doc1.computeJSDiv(doc2);
//        sentimentDiff = Math.abs(doc1.getOverallSentiment() - doc2.getOverallSentiment());
    }

    /**
     * Compare two DocumentPair objects for equality.
     *
     * //@param other is not null
     * @return true if this DocumentPair and the other DocumentPair represent
     * the same two Document objects and false otherwisinite.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj instanceof DocumentPair) {
            DocumentPair other = (DocumentPair) obj;
            return ((this.doc1.equals(other.doc1) && this.doc2.equals(other.doc2))
                    || (this.doc1.equals(other.doc2) && (this.doc2.equals(other.doc1))));
        } else {
            return false;
        }

    }

    /**
     * Compute the hashCode for a DocumentPair
     *
     * @return the hashCode for this DocumentPair
     */
    @Override
    public int hashCode() {
        return doc1.hashCode() + doc2.hashCode();
    }

    /**
     * Compare two DocumentPair objects
     *
     * @param other the other DocumentPair to compare this to
     * @return a value less than 0 if this DocumentPair is less divergent
     * internally than the other DocumentPair, 0 if the divergence of
     * the two pairs is the same, and a value > 0 if this pair is more
     * divergent than the other pair.
     */
    public int compareTo(DocumentPair other) {
        return (this.jsDiv - other.jsDiv);
    }

    /**
     * Return the first Document in the DocumentPair. The ordering of Document
     * objects in a DocumentPair is arbitrary.
     *
     * @return the first Document in the DocumentPair.
     */
    public Document getDoc1() {
        return doc1;
    }

    /**
     * Return the second Document in the DocumentPair. The ordering of Document
     * objects in a DocumentPair is arbitrary.
     *
     * @return the second Document in the DocumentPair.
     */
    public Document getDoc2() {
        return doc2;
    }

    public int getSimilarity() {
        return jsDiv;
    }

    public int getSentimentDiff() {
        return sentimentDiff;
    }

    public String toString(){
        return this.doc1.toString()+", "+this.doc2.toString();
    }

}
