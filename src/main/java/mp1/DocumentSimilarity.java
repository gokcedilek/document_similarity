package mp1;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Collections;
import java.util.stream.Stream;

public class DocumentSimilarity {

    /**
     * Determine the two documents from a given List that are most similar to
     * each other according to the Jensen-Shannon divergence score.
     *
     * @param docList is a List with at least two Document references
     * @return the DocumentPair that holds the two Documents most similar to
     * each other using the JS Divergence Score. If more than one
     * pair of Documents has the same similarity then returns any one.
     */
    public static DocumentPair closestMatch(List<Document> docList) {
        long min = 100;
        long current = 0;
        DocumentPair similarPair = new DocumentPair(docList.get(0), docList.get(1));
        for (int i = 0; i < docList.size() - 1; i++) {
            for (int j = i + 1; j < docList.size(); j++) {
                current = docList.get(i).computeJSDiv(docList.get(j));
                if (current < min) {
                    min = current;
                    similarPair = new DocumentPair(docList.get(i), docList.get(j));
                }
            }
        }
        return similarPair;
    }


    /**
     * Return the two documents that have the greatest different in sentiment
     * scores as computed using Azure Computing Services.
     *
     * @param docList is not null
     * @return the DocumentPair with the two documents that have the greatest
     * difference in sentiment scores. If two pairs have the same difference
     * then the pair that has the lower JS Divergence is returned,
     * and if there is a still a tie then any pair that is part of the tie is returned.
     */
    public static DocumentPair sentimentDiffMax(List<Document> docList) {

        int maxSent = 0;
        int sent = 0;
        DocumentPair differentPair = new DocumentPair(docList.get(0), docList.get(1));
//        HashMap<DocumentPair, Long> pairSentScores = new HashMap<>();
        ArrayList<DocumentPair> documentPairs = new ArrayList<>();
        List<Integer> sentScores = new ArrayList<>();
        for(int i = 0; i < docList.size(); i++){
            sentScores.add(docList.get(i).getOverallSentiment());
        }
        for (int i = 0; i < docList.size() - 1; i++) {
            for (int j = i + 1; j < docList.size(); j++) {
                //differentPair = new DocumentPair(docList.get(i), docList.get(j));
                sent = Math.abs(sentScores.get(i) - sentScores.get(j));
//                pairSentScores.put(new DocumentPair(docList.get(i), docList.get(j)), sent );
                documentPairs.add(new DocumentPair(docList.get(i), docList.get(j)));
                System.out.println(sent);
            }
        }
        for (DocumentPair pair : documentPairs) {
            if(pair.getSentimentDiff()>maxSent ){
                maxSent = pair.getSentimentDiff();
            }
        }
        List<DocumentPair> selectedPairs = new ArrayList<>();
        for (DocumentPair pair: documentPairs) {
            if (pair.getSentimentDiff()==maxSent){
                selectedPairs.add(pair);
            }
        }
        // return closestMatch(selectedPairs);
        int minDiv = 100;
        int currentDiv = 0;
        DocumentPair newPair;
        DocumentPair pairToReturn = new DocumentPair(selectedPairs.get(0).getDoc1(), selectedPairs.get(0).getDoc2());

        //traverse this arraylist and compute div scores
        for (int i = 0; i < selectedPairs.size(); i++) {
            newPair = new DocumentPair(selectedPairs.get(i).getDoc1(), selectedPairs.get(i).getDoc2());
            currentDiv = selectedPairs.get(i).getJsDiv();
            if (currentDiv < minDiv) {
                minDiv = currentDiv;
                pairToReturn = newPair;
            }
        }
        return pairToReturn;
    }




    /**
     * Determine a set of document groups where a group of Documents are more
     * similar to each other than to Documents in a different group.
     *
     * @param docList   is a List with at least two Document references from which we
     *                  want to group Documents by similarity
     * @param numGroups n is the number of Document groups to create and 0 < numGroups <= n
     * @return a Map that represents how Documents are grouped.
     * Two Documents that are in the same group will map to the same Document,
     * and two Documents that are not in the same group will map to different
     * Documents. Further, the Document that represents a group will have the
     * lexicographically smallest id in that group.
     */
    public static Map<Document, Document> groupSimilarDocuments(List<Document> docList, int numGroups) {

        DocumentCollection partitions = new DocumentCollection();
        ArrayList<DocumentPair> allPairs = new ArrayList<>();
        for (Document doc:docList) {
            partitions.add(doc);
        }
        for (int i = 0; i < docList.size() - 1; i++) {
            for (int j = i + 1; j < docList.size(); j++) {
                DocumentPair onePair = new DocumentPair(docList.get(i), docList.get(j));
                allPairs.add(onePair);
            }
        }
        Collections.sort(allPairs);
        int count = partitions.size();
        System.out.println(count);

            for (DocumentPair pair : allPairs) {
                if (count > numGroups) {
                    Document d1 = pair.getDoc1();
                    Document d2 = pair.getDoc2();
                    partitions.merge(d1, d2);
                    count = partitions.size();
                }
            }

            System.out.println(count);


        HashMap<Document, Document> groupSimilarity = new HashMap<Document, Document>();
        for (Document doc : docList) {
            groupSimilarity.put(doc, partitions.find(doc));
        }

        return groupSimilarity;
    }
}


