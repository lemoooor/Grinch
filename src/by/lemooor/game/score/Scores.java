package by.lemooor.game.score;

public class Scores {
    private final String[] scores = new String[10];

    public void setScore(String score, int index) {
        scores[index] = score;
    }

    public String getScore(int index) {
        return scores[index];
    }
}
