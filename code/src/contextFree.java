import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class contextFree {
    /**
     * this method does process of converting a context free grammar to Greibach normal form
     *
     * @param grammarRules rules of grammar
     */
    public void normalize(ArrayList<String> grammarRules) {
        removeLambdaRules(grammarRules);
        removeLeftRecursionRules(grammarRules);
        removeUnitRules(grammarRules);
        convertToGreibachNormalForm(grammarRules);
    }
    /**
     * this method finds null variables
     *
     * @param grammarRules rules of grammar
     * @return null variables
     */
    public ArrayList<Character> findNullVariables(ArrayList<String> grammarRules) {
        ArrayList<Character> newNullVariables = new ArrayList<>();
        ArrayList<Character> oldNullVariables = new ArrayList<>();
        for (String st : grammarRules) {
            if (st.charAt(3) == '.') {
                newNullVariables.add(st.charAt(0));
            }
        }
       while(!newNullVariables.equals(oldNullVariables)){
           oldNullVariables.clear();
           oldNullVariables.addAll(newNullVariables);
        for (String st : grammarRules) {
            int flag = 0;
            for (int i = 3; i < st.length(); i++) {
                if (!newNullVariables.contains(st.charAt(i))) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                if(!newNullVariables.contains(st.charAt(0))) {
                    newNullVariables.add(st.charAt(0));
                }
            }
        }
    }
       return newNullVariables;
    }



    /**
     * this method finds all subsets of null variables
     *
     * @param nullVariables null variables of grammar rules
     * @return all subsets
     */
    private ArrayList<String> findSubsets(ArrayList<Character> nullVariables) {
        ArrayList<String> Subsets = new ArrayList<>();
        int n = nullVariables.size();
        // Run a loop for finding all 2^n
        // subsets one by one
        for (int i = 1; i < (1 << n); i++) {
            String Subset = "";
            // find current subset
            for (int j = 0; j < n; j++) {

                // (1<<j) is a number with jth bit 1
                // so when we 'and' them with the
                // subset number we get which numbers
                // are present in the subset and which
                // are not
                if ((i & (1 << j)) > 0) {
                    Subset = Subset.concat("" + nullVariables.get(j));
                }
            }
            Subsets.add(Subset);
        }
        return Subsets;
    }

    /**
     * this method removes lambda rules
     *
     * @param grammarRules rules of grammar
     */
    public void removeLambdaRules(ArrayList<String> grammarRules) {
        ArrayList<Character> nullVariables = findNullVariables(grammarRules);
        ArrayList<String> Subsets = findSubsets(nullVariables);
        ArrayList<String> newRules = new ArrayList<>();
        for (String st : Subsets) {
            int flag = -1;
            for (int k = 0 ; k< grammarRules.size();k++) {
                if(flag == 0) {
                    k--;
                    String newRule = grammarRules.get(k).substring(0, 3);
                    for (int i = 3; i < grammarRules.get(k).length(); i++) {
                        int flag2 = 0;
                        for (int j = 0; j < st.length(); j++) {
                            if (grammarRules.get(k).charAt(i) == st.charAt(j)) {
                                flag2 = 1;
                                break;
                            }
                        }
                        if (flag2 == 0) {
                            newRule = newRule.concat("" + grammarRules.get(k).charAt(i));

                        }
                    }
                    newRules.add(newRule);
                    k++;
                }
                else{
                    flag =0;
                }
                for (int i = 0; i < st.length(); i++) {
                    if (!grammarRules.get(k).substring(3, grammarRules.get(k).length()).contains("" + st.charAt(i))) {
                        flag = 1;
                        break;
                    }
                }
            }
        }  grammarRules.addAll(newRules);
        grammarRules.removeIf(st -> st.length() == 3 || st.charAt(3) == '.');
    }
    /**
     * this method removes left recursion rules
     *
     * @param grammarRules rules of grammar
     */
    public void removeLeftRecursionRules(ArrayList<String> grammarRules) {
        ArrayList<Character> variables = new ArrayList<>();
        for (String grammarRule : grammarRules) {
            if (grammarRule.charAt(0) == grammarRule.charAt(3)) {
                if (!variables.contains(grammarRule.charAt(0))) {
                    variables.add(grammarRule.charAt(0));
                }
            }
        }
        ArrayList<Character> variablesWithLeftRecursionRules = new ArrayList<>();
        ArrayList<String> newRules = new ArrayList<>();
        for (char variable : variables) {
            ArrayList<String> leftRecursions = new ArrayList<>();
            ArrayList<String> nonLeftRecursions = new ArrayList<>();
            for (String grammarRule : grammarRules) {
                if (grammarRule.charAt(0) == variable) {
                    if (grammarRule.charAt(3) == variable) {
                        leftRecursions.add(grammarRule);
                        if (!variablesWithLeftRecursionRules.contains(variable)) {
                            variablesWithLeftRecursionRules.add(variable);
                        }
                    } else {
                        nonLeftRecursions.add(grammarRule);
                    }
                }
            }
            for (String nonLeftRecursion : nonLeftRecursions) {
                char newVariable = (char) ('Z' - variablesWithLeftRecursionRules.size() + 1);
                newRules.add(nonLeftRecursion + newVariable);
            }
            for (String leftRecursion : leftRecursions) {
                char newVariable = (char) ('Z' - variablesWithLeftRecursionRules.size() + 1);
                String newRule = newVariable + "->";
                newRule = newRule.concat(leftRecursion.substring(4, leftRecursion.length()));
                newRules.add(newRule);
                newRules.add(newRule + newVariable);
            }
        }
        Iterator<String> it = grammarRules.iterator();
        for (char variable : variablesWithLeftRecursionRules) {
            while (it.hasNext()) {
                String grammarRule = it.next();
                if (grammarRule.charAt(0) == variable && grammarRule.charAt(3) == variable) {
                    it.remove();
                }
            }
        }
        grammarRules.addAll(newRules);}


    /**
     * this method removes unit rules
     *
     * @param grammarRules rules of grammar
     */
    public void removeUnitRules(ArrayList<String> grammarRules) {
        ArrayList<String> UnitRules = new ArrayList<>();
        for (String grammarRule : grammarRules) {
            if (Character.isUpperCase(grammarRule.charAt(3)) && grammarRule.length() == 4) {
                UnitRules.add(grammarRule);
            }
        }
        ArrayList<Character> additionalVariables = new ArrayList<>();
        ArrayList<String> newRules = new ArrayList<>();
        for (String UnitRule : UnitRules) {
            if (!additionalVariables.contains(UnitRule.charAt(0))) {
                ArrayList<Character> dependency = new ArrayList<>();
                dependency.add(UnitRule.charAt(0));
                dependency.add(UnitRule.charAt(3));
                char ch = UnitRule.charAt(3);
                for (String unitRule : UnitRules) {
                    if (unitRule.charAt(0) == ch) {
                        additionalVariables.add(ch);
                    }
                }
                for (String grammarRule : grammarRules) {
                    if (grammarRule.charAt(0) == ch && Character.isUpperCase(grammarRule.charAt(3)) && grammarRule.length() == 4) {
                        dependency.add(grammarRule.charAt(3));
                        ch = grammarRule.charAt(3);
                        for (String unitRule : UnitRules) {
                            if (unitRule.charAt(0) == ch) {
                                additionalVariables.add(ch);
                            }
                        }
                    }

                }
                for (int i = dependency.size() - 1; i >= 0; i--) {
                    for (String grammarRule : grammarRules) {
                        if (grammarRule.charAt(0) == dependency.get(i) && (grammarRule.length() > 4 || Character.isLowerCase(grammarRule.charAt(3)))) {
                            for (int j = i - 1; j >= 0; j--) {
                                newRules.add(dependency.get(j) + "->" + grammarRule.substring(3, grammarRule.length()));
                            }
                        }
                    }
                }
                            grammarRules.removeIf(st -> dependency.contains(st.charAt(0)) && dependency.contains(st.charAt(3)) && st.length() == 4);
            }
        }
        grammarRules.addAll(newRules);
        ArrayList<String> temp = new ArrayList<>();
        for (String grammarRule : grammarRules) {
            if (!temp.contains(grammarRule)) {
                temp.add(grammarRule);
            }
        }
        grammarRules.clear();
        grammarRules.addAll(temp);
    }

    /**
     * this method converts context free grammar to Greibach normal form
     *
     * @param grammarRules rules of context free grammar
     */
    private void convertToGreibachNormalForm(ArrayList<String> grammarRules) {
        ArrayList<String> newRules = new ArrayList<>();
        ArrayList<String> removedRules = new ArrayList<>();
        for (int j = 0; j < grammarRules.size(); j++) {
            if (Character.isLowerCase(grammarRules.get(j).charAt(3))) {
                for (int i = 4; i < grammarRules.get(j).length(); i++) {
                    if (Character.isLowerCase(grammarRules.get(j).charAt(i))) {
                        newRules.add(Character.toUpperCase(grammarRules.get(j).charAt(i)) + "->" + grammarRules.get(j).charAt(i));
                        grammarRules.set(j, grammarRules.get(j).substring(0, 4) + Character.toUpperCase(grammarRules.get(j).charAt(4)) +
                                grammarRules.get(j).substring(5, grammarRules.get(j).length()));
                    }
                }
            } else {
                removedRules.add(grammarRules.get(j));
                for (String grammarRule : grammarRules) {
                    if (grammarRule.charAt(0) == grammarRules.get(j).charAt(3)) {
                        newRules.add(grammarRules.get(j).charAt(0) + "->" + grammarRule.substring(3, grammarRule.length())
                                + grammarRules.get(j).substring(4, grammarRules.get(j).length()));
                    }
                }
            }
        }
        ArrayList<String> temp = new ArrayList<>();
        for (String newRule : newRules) {
            if (!temp.contains(newRule)) {
                temp.add(newRule);
            }
        }
        grammarRules.removeIf(removedRules::contains);
        grammarRules.addAll(temp);
    }

    /**
     * this method checks that a word can be a member of a context free language or not by using PDA
     *
     * @param grammarRules rules of grammar
     * @param word         the word
     * @param index        index of beginning
     * @param stack        stack of our PDA
     * @return true or false
     */
    public boolean checkMembershipByPDA(ArrayList<String> grammarRules, String word, int index, Stack<Character> stack) {
        ArrayList<String> relatedRules = new ArrayList<>();
        for (String grammarRule : grammarRules) {
            if (grammarRule.charAt(3) == word.charAt(index) && stack.peek() == grammarRule.charAt(0)) {
                relatedRules.add(grammarRule);
            }
        }
        for (String relatedRule : relatedRules) {
            Stack<Character> temp = new Stack<>();
            for (Character variable : stack) {
                temp.push(variable);
            }
            temp.pop();

            for (int i = relatedRule.length() - 1; i >= 4; i--) {
                temp.push(relatedRule.charAt(i));
            }
            if (index == word.length() - 1) {
                if (temp.peek() == '$') {
                    return true;
                }
            } else {
                if (checkMembershipByPDA(grammarRules, word, index + 1, temp)) {
                    return true;
                }
            }
        }
        return false;
    }
}
