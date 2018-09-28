package mp1;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class Document implements Comparable<Document> {
    private final String docURL;
    private final String id;                 // the URL for the document
    private StringBuilder text;
    private final int MAX_CHARS= 5000;
    private final int MAX_STRINGS = 100;

    private int totalWords;
    private int sentimentScore = -1;

    private HashMap<String, Integer> wordMap;
    private TextCollection request;
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
        this.id=id;
        Scanner docScan= new Scanner(new URL(docURL).openStream());
        StringBuilder text= new StringBuilder(1000);
        wordMap= new HashMap<String, Integer>();
        totalWords=0;
        request = new TextCollection();
        int i = 0;
        while(docScan.hasNext()) {
            String word = docScan.next();

            //form the hashMap
            if (!wordMap.containsKey(word)) {
                wordMap.put(word, 1);
            } else {
                wordMap.put(word, wordMap.get(word) + 1);
            }
            totalWords++;

            //form the request
            if (request.size()<=MAX_STRINGS) {
                if ((text.length() + word.length() == MAX_CHARS ) ) {
                    text.append(word);
                    request.add(Integer.toString(i), "en", text.toString().trim());
                    text = new StringBuilder();
                    i++;
                }
                else if(text.length() + word.length() < MAX_CHARS){
                    text.append(word);
                    if(!docScan.hasNext()){
                        request.add(Integer.toString(i), "en", text.toString().trim());
                        break;
                    }

                    text.append(" ");

                }
                else {
                    request.add(Integer.toString(i), "en", text.toString().trim());

                    text = new StringBuilder(word + " ");
                    i++;
                }


            }



        }
    }

    public Document(String url) throws IOException {
        // a simpler constructor that sets the id to be the url
        this(url, url);
    }
    /**
     * Return the number of words in the document
     *
     *
     * @return the total number of words in the document
     */

    public int getTotalWords(){
        return this.totalWords;
    }
    /**
     * Return the sentiment score of the document.
     *
     *
     * @return the sentiment score of the document
     */
    public int getCalculatedSent(){
        return sentimentScore;
    }
    /**
     * Return the map of words in the document and their number of occurrences
     *
     *
     * @return the word-map of the document
     */
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
            double prob1 = calcProb(map1, s, words_1);
            double prob2 = calcProb(map2, s, words_2);
            double prob_mean = (prob1+ prob2)/2.0;
            double firstTerm=0;
            double secondTerm=0;

            if (prob1!=0){
                firstTerm = prob1*logbase2(prob1/prob_mean);
            }
            if (prob2!=0){
                secondTerm = prob2*logbase2(prob2/prob_mean);
            }
            sum+= firstTerm + secondTerm;
        }
        double JSD = sum/2;
        return Math.round(JSD*100);
    }
    /**
     * Compute the probability of occurrence of a word in the document.
     *
     * @param myMap is not null
     * @param key the word to calculate the probability of
     * @param size total number of words contained in the document
     * @return the probability of occurrence the word "key" in the document (this)

     */

    public double calcProb(HashMap<String, Integer> myMap, String key, int size){
        if(!myMap.containsKey(key)){
            return 0.0;
        }
        return (double) myMap.get(key)/ size;
    }

    /**
     * Compute the logarithm with base 2 of a given number.
     *
     * @param num is not null and nonzero
     * @return the logarithm with base 2 of a given number
     */

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
        if(this.sentimentScore != -1)
            return this.sentimentScore;

        try{
            AzureSentimentAnalysis.init();
        }catch(IOException exc1){
            System.out.println("The Azure key and/or host could not be read! ");
        }
        List<SentimentResponse> scores=  AzureSentimentAnalysis.getSentiments(request);
        System.out.println(request);

        Collections.sort(scores);
        int size= scores.size();
        int sentimentScore;

        if(size==0){
            sentimentScore=0;
        }
        else if(size==1){
            sentimentScore=scores.get(0).getScore();
        }
        else if(size %2!=0){
            sentimentScore= scores.get(size/2).getScore();
        }
        else{
            sentimentScore= (int) Math.round( (scores.get(size/2 -1).getScore() + scores.get(size/2).getScore())/2.0 ) ;
        }
        this.sentimentScore = sentimentScore;
        return sentimentScore;


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
