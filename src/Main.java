import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Difficulty askDifficulty(Scanner scanner) {

        while (true) {
            System.out.println();
            System.out.println("CHOOSE DIFFICULTY:");
            System.out.println("E) EASY   (4x4)");
            System.out.println("M) MEDIUM (6x6)");
            System.out.println("H) HARD   (8x8)");
            System.out.println();
            System.out.print("> ");

            switch (scanner.nextLine().toLowerCase().trim()) {
                case "e" -> {
                    return Difficulty.EASY;
                }
                case "m" -> {
                    return Difficulty.MEDIUM;
                }
                case "h" -> {
                    return Difficulty.HARD;
                }
                case "quit" -> {
                    return null;
                }
                default -> {}
            }
        }
    }

    public static String askPlayAgain(Scanner scanner) {
        while (true) {
            System.out.println("PLAY AGAIN?");
            System.out.println();
            System.out.println("Y) YES");
            System.out.println("N) NO");
            System.out.println("D) CHANGE DIFFICULTY");
            System.out.println();
            System.out.print("> ");

            switch (scanner.nextLine().toLowerCase().trim()) {
                case "y", "yes" -> {
                    return "YES";
                }
                case "n", "no", "quit" -> {
                    return "NO";
                }
                case "d" -> {
                    return "CHANGE_DIFFICULTY";
                }
                default -> {}
            }
        }
    }
    public static void printScores(ArrayList<Score> scores, Score last) {
        scores.sort((a,b) -> Score.byScore(a,b));
        System.out.println();
        System.out.printf("|%27s%-27s|\n","SCORE","BOARD");
        System.out.printf("|%-54s|\n","");
        for(int i = 0; i < scores.size(); i++) {
            String s = String.format("%2d.%s", i+1, scores.get(i));
            System.out.printf("|%-54s|", s);
            System.out.println(scores.get(i) == last ? "  <--" : "" );
        }
        System.out.printf("|%-54s|\n","");
        System.out.println();
    }
    public static void main(String[] args) {
        Game.showInstructions();

        ArrayList<Score> scores = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        Difficulty difficulty = askDifficulty(scanner);
        while (difficulty != null) {
            Game game = new Game(difficulty);
            Score score = game.play();
            if (score != null) scores.add(score);
            printScores(scores, score);

            switch (askPlayAgain(scanner)) {
                case "YES" -> {
                    continue;
                }
                case "NO" -> difficulty = null;
                case "CHANGE_DIFFICULTY" -> difficulty = askDifficulty(scanner);
                default -> {}
            }
        }

        System.out.println("goodbye");
    }
}