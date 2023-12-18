public enum Difficulty {
    EASY("EASY"),
    MEDIUM("MEDIUM"),
    HARD("HARD");

    private String value;
    private Difficulty(String value) {
        this.value = value;
    }

    public String toString () {
        return this.value;
    }
}
