package mp1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;


public class Document implements Comparable<Document> {
    private final String docURL;
    private final String id;                 // the URL for the document
    private StringBuilder text;
    private final int MAX_CHARS= 5000;

    private int totalWords;
    private int sentimentScore;
    private HashMap<String, Integer> wordMap;
    // You may need more fields for a document.

    /**
     * Create a document given a URL to the document's text as well as an id
     *
     * @param id is not null and is not the empty
     * @param
     * url is not null and is not the empty string
     * @throws IOException
     */
    public Document(String id, String url) throws IOException {
        assert (id != null);
        assert (!id.equals(""));
        assert (url != null);
        assert (!url.equals(""));

        this.docURL = url;
        this.id=docURL;
        Scanner docScan= new Scanner(new URL(docURL).openStream());
        text= new StringBuilder();
        wordMap= new HashMap<String, Integer>();
        totalWords=0;
        while(docScan.hasNext()) {
            String word = docScan.next();
            //the scanner is reading the whole document correctly!
            //text.append(word);
            if (!wordMap.containsKey(word)) {
                wordMap.put(word, 1);
            } else {
                wordMap.put(word, wordMap.get(word) + 1);
            }
            totalWords=totalWords+1;
        }

//        ArrayList<String> pieces= new ArrayList<>();
//        int index=MAX_CHARS;
//        while(index>0 || Character.isWhitespace(text.charAt(index))){
//           int i=0;
//
//            index--;
//        }

    }

    public int getTotalWords(){
        return this.totalWords;
    }

    public Document(String url) throws IOException {
        // a simpler constructor that sets the id to be the url
        this(url, url);
    }
    public HashMap<String,Integer> getWordMap(){
        return this.wordMap;
    }

    /**
     * Compute the Jensen-Shannon Divergence between the two textList
     * on the basis of the words used.
     *
     * @param otherDoc is not null
     * @return the Jensen-Shannon divergence multiplied by 100 and rounded to the nearest integer
     */
    public long computeJSDiv(Document otherDoc) {
        HashMap<String, Integer> map1 = this.getWordMap();
        int words_1= this.getTotalWords();
        HashMap<String, Integer> map2 = otherDoc.getWordMap();
        int words_2= otherDoc.getTotalWords();
        HashSet<String> merged= new HashSet<>();
        merged.addAll(map1.keySet());
        merged.addAll(map2.keySet());
        double sum=0;
        for(String s:merged){
            double prob1= calcProb(map1, s, words_1);
            double prob2= calcProb(map2, s, words_2);
            double prob_mean= (prob1+ prob2)/2;
            sum+= prob1*logbase2(prob1/prob_mean) + prob2*logbase2(prob2/prob_mean);
        }
        return Math.round(sum*50);
    }




    public double calcProb(HashMap<String, Integer> myMap, String key, Integer size){
        if(!myMap.containsKey(key)){
            return 0;
        }
        return (double) myMap.get(key)/ size;
    }
    public double logbase2(double num){
        return Math.log(num)/Math.log(2);
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
