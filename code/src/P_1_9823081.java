import java.util.ArrayList;
import java.util.Scanner;

/**
 * this class implements Touring machine of section ii of Problem 1
 */
public class P_1_9823081 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //input of f(x)
        int x;
        while (true) {
            System.out.println("x = ");
            x = sc.nextInt();
            //Tape of input
            ArrayList<String> Tape = new ArrayList<>();
            Tape.add("B");
            for (int i = 1; i <= x; i++) {
                Tape.add("1");
            }
            Tape.add("B");
            if (x % 2 == 1) {
                Tape.add("B");
                plusOne(Tape);
            }
            halve(Tape);
            System.out.print("f(" + x + ")= ...Blank ");
            for (int i = 1; i < x; i++) {
                if (Tape.get(i).equals("1")) {
                    System.out.print("1 ");
                } else {
                    break;
                }
            }
            System.out.println("Blank...");
            int select = 3;
            System.out.println("1:continue" + '\n' + "2:exit");
            do {
                select = sc.nextInt();
            } while (select != 1 && select != 2);
            if (select == 2) {
                System.out.println("Goodbye.coming back soon :)");
                break;
            }
        }
    }
    /**
     * this method adds a "1" to Tape
     *
     * @param Tape Tape of input
     */
    private static void plusOne(ArrayList<String> Tape) {
        int j = 1;
        String Q = "q0";
        while (true) {
            if (Q.equals("q0") && Tape.get(j).equals("1")) {
                Q = "q0";
                Tape.set(j, "1");
                j++;
            } else if (Q.equals("q0") && Tape.get(j).equals("B")) {
                Q = "q1";
                Tape.set(j, "1");
                j--;
            } else if (Q.equals("q1") && Tape.get(j).equals("1")) {
                Q = "q1";
                Tape.set(j, "1");
                j--;
            } else if (Q.equals("q1") && Tape.get(j).equals("B")) {
                Tape.set(j, "B");
                break;
            }
        }
    }

    /**
     * this method halves the number which specified in Tape in unary form
     *
     * @param Tape Tape of input or tape of input + 1
     */
    private static void halve(ArrayList<String> Tape) {
        int j = 1;
        String Q = "q2";
        while (true) {
            if (Q.equals("q2") && Tape.get(j).equals("1")) {
                Q = "q3";
                Tape.set(j, "X");
                j++;
            } else if (Q.equals("q3") && Tape.get(j).equals("1")) {
                Q = "q3";
                Tape.set(j, "X");
                j++;
            } else if (Q.equals("q3") && Tape.get(j).equals("B")) {
                Q = "q4";
                Tape.set(j, "B");
                j--;
            } else if (Q.equals("q4") && Tape.get(j).equals("X")) {
                Q = "q4";
                Tape.set(j, "X");
                j--;
            } else if (Q.equals("q4") && Tape.get(j).equals("B")) {
                Q = "q5";
                Tape.set(j, "B");
                j++;
                break;
            }
        }
        while (true) {
            if (Q.equals("q5") && Tape.get(j).equals("X")) {
                Q = "q6";
                Tape.set(j, "1");
                j++;
            } else if (Q.equals("q6") && Tape.get(j).equals("X")) {
                Q = "q6";
                Tape.set(j, "X");
                j++;
            } else if (Q.equals("q6") && Tape.get(j).equals("B")) {
                Q = "q7";
                Tape.set(j, "B");
                j--;
            } else if (Q.equals("q7") && Tape.get(j).equals("X")) {
                Q = "q8";
                Tape.set(j, "B");
                j--;
            } else if (Q.equals("q8") && Tape.get(j).equals("X")) {
                Q = "q8";
                Tape.set(j, "X");
                j--;
            } else if (Q.equals("q8") && Tape.get(j).equals("1")) {
                Q = "q5";
                Tape.set(j, "1");
                j++;
            } else if (Q.equals("q5") && Tape.get(j).equals("B")) {
                Tape.set(j, "B");
                break;
            }}}}

