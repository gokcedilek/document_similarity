package mp1;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class MP1TestPublic {

    private static Document studyInScarlet;
    private static Document tempest;
    private static Document persuasion;
    private static Document prideAndPrejudice;
    private static Document arcticCircle;
    private static Document blackPeter;
    private static Document flossieFumble;
    private static Document aesop;
    private static Document lastFreeMan;
    private static Document immortality;
    private static Document sixNapoleons;
    private static List<Document> docList;

    @BeforeClass
    public static void setup() throws IOException {
        docList = new ArrayList<Document>();

        studyInScarlet = new Document("Conan Doyle: A Study in Scarlet", "http://www.gutenberg.org/files/244/244-0.txt");
        docList.add(studyInScarlet);

        blackPeter = new Document("Conan Doyle: Adventure of Black Peter", "http://textfiles.com/stories/blackp.txt");
        docList.add(blackPeter);

        tempest = new Document("Shakespeare: The Tempest", "http://www.gutenberg.org/files/23042/23042-0.txt");
        docList.add(tempest);

        persuasion = new Document("Austen: Persuasion", "http://www.gutenberg.org/cache/epub/105/pg105.txt");
        docList.add(persuasion);

        prideAndPrejudice = new Document("Austen: Pride and Prejudice", "http://www.gutenberg.org/files/1342/1342-0.txt");
        docList.add(prideAndPrejudice);

        arcticCircle = new Document("Prentice: The Arctic Circle and Beyond", "http://textfiles.com/stories/arctic.txt");
        docList.add(arcticCircle);

        flossieFumble = new Document("Flossie Fumble: Letter to Blossom", "http://textfiles.com/stories/blossom.pom");
        docList.add(flossieFumble);

        aesop = new Document("Aesop: Fables", "http://textfiles.com/stories/aesop11.txt");
        docList.add(aesop);

        lastFreeMan = new Document("Porretto: The Last Free Man", "http://textfiles.com/stories/freeman.fil");
        docList.add(lastFreeMan);

        immortality = new Document("Burroughs: Immortality", "http://textfiles.com/stories/immortal");
        docList.add(immortality);

        sixNapoleons = new Document("Conan Doyle: The Adventure of the Six Napoleons", "http://textfiles.com/stories/6napolen.txt");
        docList.add(sixNapoleons);

    }
    @Test
    public void printTest(){
        for(int i=0; i<docList.size(); i++){
            if(docList.get(i).equals(null)){
                System.out.println("The document is null" + docList.get(i).toString());
            }
        }
    }


    @Test
    public void testJSDiv1() {
        assertEquals(30, blackPeter.computeJSDiv(studyInScarlet));
    }

    @Test
    public void testJSDiv2() {
        assertEquals(35, prideAndPrejudice.computeJSDiv(blackPeter));
    }

    @Test
    public void testJSDiv3() {
        assertEquals(0, prideAndPrejudice.computeJSDiv(prideAndPrejudice));
    }

    @Test
    public void testSimilarity1() {
        assertEquals(new DocumentPair(prideAndPrejudice, studyInScarlet), DocumentSimilarity.closestMatch(docList));
    }

    @Test
    public void testSentiment1() {
        assertEquals(82, prideAndPrejudice.getOverallSentiment());
    }

    @Test
    public void testSentimentDiff1() {
        DocumentPair docPair = DocumentSimilarity.sentimentDiffMax(docList);
        DocumentPair expPair = new DocumentPair(studyInScarlet, prideAndPrejudice);
        assertEquals(expPair, docPair);
    }

//    @Test
//    public void testGrouping1() {
//        HashMap<Document, Document> expMap = new HashMap<>();
//        expMap.put(blackPeter, aesop);
//        expMap.put(tempest, tempest);
//        expMap.put(prideAndPrejudice, aesop);
//        expMap.put(arcticCircle, arcticCircle);
//        expMap.put(aesop, aesop);
//        expMap.put(immortality, immortality);
//        expMap.put(sixNapoleons, aesop);
//        assertEquals(expMap, DocumentSimilarity.groupSimilarDocuments(docList, 4));
//    }

}