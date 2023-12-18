public class Score {
    private Difficulty difficulty;
    private int timeInSeconds;
    private int numOfFlips;
    private int numOfTurns;
    private int scoreValue;

    public static int byScore (Score a, Score b) {
        if (a.getScoreValue() < b.getScoreValue()) return -1;
        else if (a.getScoreValue() > b.getScoreValue()) return 1;
        else return 0;
    }
    public Score (Difficulty difficulty, int timeInSeconds, int numOfFliips) {
        this.difficulty = difficulty;
        this.timeInSeconds = timeInSeconds;
        this.numOfFlips = numOfFliips;
        this.scoreValue = timeInSeconds * 5 + numOfFliips * 10;
    }

    public int getTimeInSeconds () {
        return timeInSeconds;
    }

    public String getTimeAsString () {
        String min = String.format("%2d", timeInSeconds/60);
        String sec = String.format("%2d", timeInSeconds%60);
        return min + "m" + sec + "s";
        //return timeInSeconds/60 + "m" + timeInSeconds%60 + "s";
    }
    public int getNumOfFlips() {
        return numOfFlips;
    }
    public int getScoreValue() {
        return scoreValue;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String toString() {
        return String.format("%12d%12s%12d%12s",
                getScoreValue(),
                getDifficulty(),
                getNumOfFlips(),
                getTimeAsString()
        );
        //return getScoreValue() + "\t\t\t" + getDifficulty().toString().toUpperCase() + "\t\t\t" + getNumOfFlips() + "\t\t\t" + getTimeAsString();
    }
}
