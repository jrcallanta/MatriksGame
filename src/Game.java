import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Game {
    private final MatriksSquare matriks;
    private Difficulty difficulty;
    private int numOfFlips;
    private int numOfTurns;
    private Date startTime;
    private Date endTime;
    private enum InstructionDepth {
        FULL, SHORT, TURN
    }

    public Game() {
        this.matriks = new MatriksSquare(2);
        this.numOfFlips = 0;
        this.numOfTurns = 0;
    }

    public Game(Difficulty difficulty) {
        switch (difficulty) {
            case EASY -> {
                this.matriks = new MatriksSquare(2);
                this.difficulty = Difficulty.EASY;
            }
            case MEDIUM -> {
                this.matriks = new MatriksSquare(3);
                this.difficulty = Difficulty.MEDIUM;
            }
            case HARD -> {
                this.matriks = new MatriksSquare(4);
                this.difficulty = Difficulty.HARD;
            }

            default -> {
                this.matriks = new MatriksSquare();
                this.difficulty = Difficulty.EASY;
            }
        }
        this.numOfFlips = 0;
        this.numOfTurns = 0;
    }

    public static void showInstructions(){
        showInstructions(InstructionDepth.FULL);
    }
    private static void showInstructions(InstructionDepth depth) {
        switch (depth) {
            case FULL -> {
                System.out.println();
                System.out.println("============================================");
                System.out.println("[GOAL]: The matrix board has 2N * 2N elements.");
                System.out.println("  A corner sum is calculated by adding all the");
                System.out.println("  elements in the first N rows and N columns.");
                System.out.println("  This can also be visualized as the top left");
                System.out.println("  N x N corner of the matrix board. The goal is");
                System.out.println("  to manipulate the matrix's rows and columns");
                System.out.println("  to reach the max possible corner sum. This");
                System.out.println("  target sum is shown on each turn.");
                System.out.println();
                System.out.println("[INSTRUCTIONS]: The player can flip any");
                System.out.println("  row or column any number of times.");
                System.out.println();
                System.out.println("  To flip ROWs, type 'r' followed by the");
                System.out.println("  row number(s).");
                System.out.println("  To flip COLs, type 'c' followed by the");
                System.out.println("  column number(s).");
                System.out.println();
                System.out.println("  ex.   r123      ->  flip rows 1, 2, and 3");
                System.out.println("        c24       ->  flip cols 2 and 4");
                System.out.println("        r2c34r2   ->  flip row 2 then");
                System.out.println("                      flip col 3 and 4 then");
                System.out.println("                      flip row 2");
                System.out.println();
                System.out.println("============================================");
                System.out.println();
            }

            case SHORT -> {
                System.out.println("[INSTRUCTIONS]: The player can flip any");
                System.out.println("  row or column any number of times. A flip");
                System.out.println("  is entered in the following format: ");
                System.out.println();
                System.out.println("  ROW r1 r2 r3... ");
                System.out.println("  or ");
                System.out.println("  COL c1 c2 c3... ");
                System.out.println();
            }
        }
    }

    private Score getScore () {
        System.out.println("TARGET REACHED!");
        System.out.println("YOU WIN!");
        int seconds = (int) TimeUnit.SECONDS.convert(this.endTime.getTime() - this.startTime.getTime(), TimeUnit.MILLISECONDS);
        System.out.println("TIME: " + seconds/60 + "m " + seconds%60 + "s");
        System.out.println("FLIPS: " + this.numOfFlips);
        return new Score(this.difficulty, seconds, this.numOfFlips);
    }


    private void countDown() {
        try {
            for (int i = 0; i < 3; i++) {
                //System.out.println(3 - i + "...");
                switch (i) {
                    case 0 -> System.out.println("READY...");
                    case 1 -> System.out.println("SET...");
                    case 2 -> System.out.println("GO!!!\n");
                    default -> {}
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            // do nothing
        }
    }
    public Score play() {
        showInstructions(InstructionDepth.FULL);

        countDown();
        this.matriks.print();
        this.matriks.printDetails();
        this.startTime = new Date();

        System.out.print("FLIP: ");
        Scanner scanner = new Scanner(System.in);
        String cmd = scanner.nextLine();
        Pattern cmdPat = Pattern.compile("(([R|C])([1-" + this.matriks.getMatrixSize() + "])+[\s*]?|QUIT)", Pattern.CASE_INSENSITIVE);
        Matcher cmdMat = cmdPat.matcher(cmd);
        while(true) {
            // Find if Valid Cmd Exists
            while(!cmdMat.find()) {
                System.out.println();
                showInstructions(InstructionDepth.SHORT);
                cmd = scanner.nextLine();
                cmdMat = cmdPat.matcher(cmd);
            }

            // Check if entire Cmd is Only Valid Cmd's
            String cmds = "";
            do cmds += cmdMat.group(1);
            while(cmdMat.find());
            if (!cmd.equals(cmds)) {
                showInstructions(InstructionDepth.SHORT);
                cmd = scanner.nextLine();
                cmdMat = cmdPat.matcher(cmd);
                continue;
            }

            this.numOfTurns++;
            // Reset Matcher
            cmdMat = cmdPat.matcher(cmd);
            cmdMat.find();
            do {
                switch (cmdMat.group(1).toLowerCase().charAt(0)) {
                    case 'r' -> {
                        String rows = cmdMat.group(1).substring(1).trim();
                        for(int i = 0; i < rows.length(); i++)
                            matriks.flipRow(Integer.parseInt(rows.substring(i, i + 1)) - 1);
                        this.numOfFlips++;
                    }
                    case 'c' -> {
                        String cols = cmdMat.group(1).substring(1).trim();
                        for(int i = 0; i < cols.length(); i++)
                            matriks.flipCol(Integer.parseInt(cols.substring(i, i + 1)) - 1);
                        this.numOfFlips++;
                    }
                    case 'q' -> {
                        return null;
                    }
                    default -> {}
                }
            } while (cmdMat.find());

            this.matriks.print();
            this.matriks.printDetails();
            System.out.println("FLIPS: " + this.numOfFlips);

            if (!matriks.targetReached()) {
                System.out.print("FLIP: ");
                cmd = scanner.nextLine();
                cmdMat = cmdPat.matcher(cmd);
                System.out.println("------------");
                continue;
            }

            this.endTime = new Date();
            System.out.println();
            return this.getScore();
        }
    }
}
