import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class P_2_9823081 {
    public static void main(String[] args) {
        while (true) {
            System.out.println("1:check regularity" + '\n' + "2:check being context free" + '\n' + "3:Quit");
            Scanner sc = new Scanner(System.in);
            String select = "";
            do {
                select = sc.next();
            } while (!select.equals("1") && !select.equals("2") && !select.equals("3"));
            if (select.equals("1")){
                ArrayList<String> grammarRules = new ArrayList<>();
                System.out.println("please enter your rules one by one(with this format : leftSide->rightSide) and enter -1 at the end");
                int flag = 0;
                while (flag != -1) {
                    String grammarRule = sc.next();
                    if (grammarRule.equals("-1")) {
                        flag = -1;
                    } else {
                        if (grammarRule.charAt(1) == '-' && grammarRule.charAt(2) == '>' && Character.isUpperCase(grammarRule.charAt(0))) {
                            int counter = 0;
                            for(int i = 3 ; i <grammarRule.length();i++){
                                if(Character.isUpperCase(grammarRule.charAt(i))){
                                    counter++;
                                }
                            }
                            if(counter <=1) {
                                grammarRules.add(grammarRule);
                            }
                            else{
                                System.out.println(" input grammar is not regular");
                            }
                        } else {
                            System.out.println("invalid format or input grammar is not regular");
                            flag = -1;
                        }
                    }
                }
                if(grammarRules.isEmpty()){
                    continue;
                }
                System.out.println("input grammar is regular");
                String sel = "";
                contextFree contextfree = new contextFree();
                regular reg = new regular();
                ArrayList<Character> finalStates = new ArrayList<>(contextfree.findNullVariables(grammarRules));
                finalStates.add('f');
                contextfree.removeLambdaRules(grammarRules);
                reg.convertToSimpleForm(grammarRules);
                reg.change(grammarRules);
                contextfree.removeUnitRules(grammarRules);
                while (true) {
                    System.out.println("do you want to check membership of a word?" + '\n' + "1:yes" + '\n' + "2:No");
                    do {
                        sel = sc.next();
                    } while (!sel.equals("1") && !sel.equals("2"));
                    if (sel.equals("2")) {
                        break;
                    } else {
                        System.out.println("please enter the word:");
                        String word = sc.next();
                        if (reg.checkMembershipByFA(grammarRules,finalStates, word, 0, 'S')) {
                            System.out.println("accepted");
                        } else {
                            System.out.println("Not accepted");
                        }
                    }
                }
            }
            else if (select.equals("2")) {
                ArrayList<String> grammarRules = new ArrayList<>();
                System.out.println("please enter your rules one by one(with this format : leftSide->rightSide) and enter -1 at the end");
                int flag = 0;
                while (flag != -1) {
                    String grammarRule = sc.next();
                    if (grammarRule.equals("-1")) {
                        flag = -1;
                    } else {
                        if (grammarRule.charAt(1) == '-' && grammarRule.charAt(2) == '>' && Character.isUpperCase(grammarRule.charAt(0))) {
                            grammarRules.add(grammarRule);
                        } else {
                            System.out.println("invalid format or input grammar is not context free");
                            flag = -1;
                        }
                    }
                }
                if(grammarRules.isEmpty()){
                    continue;
                }
                System.out.println("input grammar is context free");
                String sel = "";
                contextFree contextfree = new contextFree();
                contextfree.normalize(grammarRules);
                while (true) {
                    System.out.println("do you want to check membership of a word?" + '\n' + "1:yes" + '\n' + "2:No");
                    do {
                        sel = sc.next();
                    } while (!sel.equals("1") && !sel.equals("2"));
                    if (sel.equals("2")) {
                        break;
                    } else {
                        System.out.println("please enter the word:");
                        String word = sc.next();
                        Stack<Character> stack = new Stack<>();
                        stack.push('$');
                        stack.push('S');
                        if (contextfree.checkMembershipByPDA(grammarRules, word, 0, stack)) {
                            System.out.println("accepted");
                        } else {
                            System.out.println("Not accepted");
                        }
                    }
                }
            }
                else {
                System.out.println("Goodbye.coming back soon :)");
                break;
            }
        }
    }
}
