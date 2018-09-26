package mp1;

import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

/**
 * This data type maintains collection of disjoint document sets.
 *
 * One can add add a new document to a DocumentCollection, and this
 * creates a new set within the DocumentCollection.
 *
 * It is possible to determine whether two documents are in the same group,
 * and to merge two sets given one representative document from each set.
 */

// ** DO NOT CHANGE! **

public class DocumentCollection {

    private List<DocGroup> documentGroups = new LinkedList<DocGroup>();

    /**
     * This is a helper type for maintaining a set of documents.
     */
    private class DocGroup {
        Document head;
        Set<Document> docSet = new HashSet<Document>();

        /**
         * Create a new document group with a single document
         *
         * @param doc is not null
         */
        public DocGroup(Document doc) {
            head = doc;
            this.add(doc);
        }

        /**
         * Add a new document to this group
         *
         * @param doc is not null
         */
        public void add(Document doc) {
            docSet.add(doc);
            if (head.compareTo(doc) > 0) {
                head = doc;
            }
        }

        /**
         * Return the document that is the head (representative) for this group
         *
         * @return the head document for the group
         */
        public Document getHead() {
            return head;
        }


        /**
         * Merge a group with another group,
         * changing the group and leaving the other group unchanged
         *
         * @param other is not null
         */
        public void merge(DocGroup other) {
            docSet.addAll(other.docSet);
            if (this.head.compareTo(other.head) > 0) {
                head = other.head;
            }
        }

        /**
         * Check if the group contains a document
         *
         * @param doc
         * @return true if doc is in the group and false otherwise
         */
        public boolean contains(Document doc) {
            if (doc == null) {
                return false;
            }
            return docSet.contains(doc);
        }

    }


    /**
     * Add a new document to a set of DocumentCollection. If the document already exists in some group then nothing is changed.
     * otherwise a new group is created with the single document.
     * @param doc is not null
     */
    public void add(Document doc) {
        if (this.find(doc) == null) {
            documentGroups.add(new DocGroup(doc));
        }
    }

    /**
     * Find the group that a document is in. The group a document is in is represented by the
     * head of that group. If the document does not exist in any group then null is returned.
     * It is dangerous to return null but is a simplification for now.
     *
     * @param doc is not null
     * @return a Document that is the representative of the group doc is in.
     *          null is returned if doc is not in any group.
     */
    public Document find(Document doc) {
        for (DocGroup dg: documentGroups) {
            if (dg.contains(doc)) {
                return dg.getHead();
            }
        }
        // if we reach this far then the document was not found
        return null; // this is not convenient but sufficient for now
    }

    /**
     * A helper method for finding the DocGroup doc is in.
     * @param doc is not null
     * @return the DocGroup that doc is in
     * @throws NotFoundException if doc is not found in any DocGroup
     */
    private DocGroup findGroup(Document doc) throws NotFoundException {
        for (DocGroup dg: documentGroups) {
            if (dg.contains(doc)) {
                return dg;
            }
        }
        // if we reach this far then the document was not found
        throw new NotFoundException();
    }

    /**
     * Merge the groups that contain two documents. If the documents do not exist in the
     * collection then nothing is done. Otherwise the group with d1 and the group with d2 are
     * merged.
     *
     * @param d1 is not null
     * @param d2 is not null
     */
    public void merge(Document d1, Document d2) {
        try {
            DocGroup g1 = findGroup(d1);
            DocGroup g2 = findGroup(d2);
            if (g1 != g2) {
                g1.merge(g2);
                this.documentGroups.remove(g2);
            }
        }
        catch (NotFoundException nfe) {
            // do nothing per spec
        }
    }

    /**
     * This is our private exception class when we do not find something
     * in a group
     */
    private class NotFoundException extends Exception {
        // simple type for convenience
    }

    public int size(){
        return  this.documentGroups.size();
    }
}
