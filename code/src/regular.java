import java.util.ArrayList;

public class regular {
    /**
     * this method change for e.g A->bbB to A->bC ,C->bB
     * @param grammarRules rules of grammar
     */
    public void convertToSimpleForm(ArrayList<String>grammarRules){
        ArrayList<String>newRules = new ArrayList<>();
        int count = - 1;
        for(int i = 0 ; i <grammarRules.size();i++){
            if(grammarRules.get(i).length()>=5 && Character.isLowerCase(grammarRules.get(i).charAt(4))){
                count++;
                String st = grammarRules.get(i).substring(4,grammarRules.get(i).length());
                char newVariable1 = (char) ('M' +count);
                grammarRules.set(i,grammarRules.get(i).substring(0,4) + newVariable1) ;
                char newVariable2 ='.';
                if(st.length() == 1){
                    newRules.add(newVariable1 + "->" +st);
                }
                for(int j = 0 ; j<st.length()-1;j++){
                    if(j == st.length()-2){
                        newRules.add(newVariable1 + "->" +st.substring(j,j+2));
                    }
                    else {
                        count++;
                        newVariable2 = (char) ('M' + count);
                        newRules.add(newVariable1 + "->" + st.charAt(j) + newVariable2);
                        newVariable1 = newVariable2;

                    }
                }
            }
        }
        grammarRules.addAll(newRules);
    }

    /**
     *  this method change for e.g A->b to A->bf where f is a final state
     * @param grammarRules rules of grammar
     */
    public void change(ArrayList<String>grammarRules){
        for(int i = 0 ; i<  grammarRules.size();i++){
            if(grammarRules.get(i).length() == 4 && Character.isLowerCase(grammarRules.get(i).charAt(3))){
                grammarRules.set(i,grammarRules.get(i) + "f");
            }
        }
    }

    /**
     * this method checks that a word can be a member of a regular language or not by using FA
     * @param grammarRules rules of grammar
     * @param word         the word
     * @param index        index of beginning
     * @param Q state of FA
     * @return true or false
     */
    public boolean checkMembershipByFA(ArrayList<String> grammarRules, ArrayList<Character>finalStates,String word, int index, char Q) {
        ArrayList<String> relatedRules = new ArrayList<>();
        for (String grammarRule : grammarRules) {
            if (grammarRule.charAt(3) == word.charAt(index) && Q == grammarRule.charAt(0)) {
                relatedRules.add(grammarRule);
            }
        }
        for (String relatedRule : relatedRules) {
            char temp = relatedRule.charAt(4);
            if (index == word.length() - 1) {
                if (finalStates.contains(temp)){
                    return true;
                }
            } else {
                if (checkMembershipByFA(grammarRules,finalStates, word, index + 1, temp)) {
                    return true;
                }
            }
        }
        return false;
    }
}

