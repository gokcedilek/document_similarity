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
    private int sentimentScore;

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
        this.id=docURL;
        Scanner docScan= new Scanner(new URL(docURL).openStream());
        text= new StringBuilder();
        wordMap= new HashMap<String, Integer>();
        totalWords=0;
        request = new TextCollection();
        //request= new ArrayList<>();
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
            if (request.size()<MAX_STRINGS) {
                if (text.length() + word.length()+1 < MAX_CHARS) {
                    //check for the very last word
                    text.append(word);
                    text.append(" ");
                }
                else {
                    //int i = request.size();
                    System.out.println(text.length());
                    System.out.println(i);
                    request.add(Integer.toString(i), "en", text.toString());
                    text = new StringBuilder();
                    i++;
                }


            }


        }
    }

    public Document(String url) throws IOException {
        // a simpler constructor that sets the id to be the url
        this(url, url);
    }

    public int getTotalWords(){
        return this.totalWords;
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


    public double calcProb(HashMap<String, Integer> myMap, String key, int size){
        if(!myMap.containsKey(key)){
            return 0.0;
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
        try{
            AzureSentimentAnalysis.init();
        }catch(IOException exc1){
            System.out.println("The Azure key and/or host could not be read! ");
        }
        List<SentimentResponse> responses=  AzureSentimentAnalysis.getSentiments(request);
        ArrayList<Integer> scores= new ArrayList<>();

        for(SentimentResponse oneResponse: responses){
            Integer score= oneResponse.getScore();
            scores.add(score);
        }
        Collections.sort(scores);
        int size= scores.size();

        if(size==0){
            sentimentScore=0;
        }
        else if(size==1){
            sentimentScore=scores.get(0);
        }
        else if(size %2!=0){
            sentimentScore= scores.get(size/2);
        }
        else{
            sentimentScore= (int) Math.round( (scores.get(size/2 -1) + scores.get(size/2))/2.0 ) ;
        }
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
