package mp1;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

        import static org.junit.Assert.assertEquals;
        import org.junit.BeforeClass;
        import org.junit.Test;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;

        import static org.junit.Assert.*;

public class MP1TestPublic2 {

    private static Document studyInScarlet;
    private static Document blackPeter;
    private static Document tempest;
    private static Document persuasion;
    private static Document flossieFumble;

    private static List<Document> docList2;

    @BeforeClass
    public static void setup() throws IOException {
        docList2 = new ArrayList<Document>();

        studyInScarlet = new Document("Conan Doyle: A Study in Scarlet", "http://www.gutenberg.org/files/244/244-0.txt");
        docList2.add(studyInScarlet);

        blackPeter = new Document("Conan Doyle: Adventure of Black Peter", "http://textfiles.com/stories/blackp.txt");
        docList2.add(blackPeter);

        tempest = new Document("Shakespeare: The Tempest", "http://www.gutenberg.org/files/23042/23042-0.txt");
        docList2.add(tempest);

        persuasion = new Document("Austen: Persuasion", "http://www.gutenberg.org/cache/epub/105/pg105.txt");
        docList2.add(persuasion);

        flossieFumble = new Document("Flossie Fumble: Letter to Blossom", "http://textfiles.com/stories/blossom.pom");
        docList2.add(flossieFumble);
    }

    @Test
    public void testJSDiv1() {
        assertEquals(60, flossieFumble.computeJSDiv(studyInScarlet));
    }

    @Test
    public void testJSDiv2() {
        assertEquals(60, flossieFumble.computeJSDiv(blackPeter));
    }

    @Test
    public void testJSDiv3() {
        assertEquals(0, flossieFumble.computeJSDiv(flossieFumble));
    }

    @Test
    public void testJSDiv4() {
        assertEquals(69, tempest.computeJSDiv(flossieFumble));
    }

    @Test
    public void testJSDiv5() {
        assertEquals(44, tempest.computeJSDiv(persuasion));
    }

    @Test
    public void testJSDiv6() {
        assertEquals(62, flossieFumble.computeJSDiv(persuasion));
    }

    @Test
    public void testJSDiv7() {
        assertEquals(34, blackPeter.computeJSDiv(persuasion));
    }

    public void testJSDiv8() {
        assertEquals(51, blackPeter.computeJSDiv(tempest));
    }

    public void testJSDiv9() {
        assertEquals(26, studyInScarlet.computeJSDiv(persuasion));
    }

    public void testJSDiv10() {
        assertEquals(30, studyInScarlet.computeJSDiv(blackPeter));
    }

    public void testJSDiv11() {
        assertEquals(42, studyInScarlet.computeJSDiv(tempest));
    }

    @Test
    public void testSimilarity1() {
        assertEquals(new DocumentPair(studyInScarlet, persuasion), DocumentSimilarity.closestMatch(docList2));
    }

    @Test
    public void testSentiment1() {
        assertEquals(50, blackPeter.getOverallSentiment());
    }

    @Test
    public void testSentiment2() {
        assertEquals(20, studyInScarlet.getOverallSentiment());
    }

    @Test
    public void testSentiment3() {
        assertEquals(50, tempest.getOverallSentiment());
    }

    @Test
    public void testSentiment4() {
        assertEquals(50, persuasion.getOverallSentiment());
    }

    @Test
    public void testSentiment5() {
        assertEquals(26, flossieFumble.getOverallSentiment());
    }

    @Test
    public void testSentimentDiff1() {
        DocumentPair docPair = DocumentSimilarity.sentimentDiffMax(docList2);
        DocumentPair expPair = new DocumentPair(studyInScarlet, persuasion);
        assertEquals(expPair, docPair);
    }


    }
