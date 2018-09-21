package mp1;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Collections;

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
        // TODO: Implement this method
        return null;
    }


    /**
     * Return the two documents that have the greatest different in sentiment
     * scores as computed using Azure Computing Services.
     *
     * @param docList is not null
     * @return the DocumentPair with the two documents that have the greatest
     *          difference in sentiment scores. If two pairs have the same difference
     *          then the pair that has the lower Jensen-Shannon divergence is returned,
     *          and if there is a still a tie then any pair that is part of the tie is returned.
     */
    public static DocumentPair sentimentDiffMax(List<Document> docList) {
        // TODO: Implement this method
        return null;
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
        // TODO: Implement this method
        return null;
    }

}

