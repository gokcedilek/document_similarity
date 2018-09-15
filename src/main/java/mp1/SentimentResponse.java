package mp1;

// This class represents a sentiment response that we get from Azure Cognitive Services
// You may need to use the getScore() method from this class
// ** DO NOT CHANGE **

public class SentimentResponse implements Comparable<SentimentResponse> {
    private double score;
    private int id;

    public SentimentResponse() {
        // empty constructor
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return (int) Math.round(100 * score);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id: " + id + ", score: " + getScore();
    }

    @Override
    public int compareTo(SentimentResponse other) {
        if (this.score < other.score) {
            return -1;
        } else {
            if (this.score > other.score) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
