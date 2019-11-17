public class Trie implements SearchAndInsert {

    private static final int ALPHABET_SIZE = 27;
    private TrieNode root = new TrieNode();

    /*****************
     *TrieNode Class
     ****************/
    class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];
        boolean isEndOfWord;

        TrieNode() {
            isEndOfWord = false;
            for(int i = 0; i< ALPHABET_SIZE; i++) {
                children[i] = null;
            }
        }
    }

    /********************************************
     *Trie insert
     * creates a new TrieNode for each character
     * of every word at unique index, and at end
     * of word, sets boolean to true
     ********************************************/
    @Override
    public void insert(String key) {
        int currChar;
        int length = key.length();
        int index;
        TrieNode curr = root;

        for(currChar = 0; currChar < length; currChar++) {
            index = key.charAt(currChar) - 'a';
            if(index == -58) { //apostrophe - 'a' = -58
                index = 26;
            }
            if(curr.children[index] == null)
                curr.children[index] = new TrieNode();

            curr = curr.children[index];
        }
        curr.isEndOfWord = true;
    }

    /*******************************************************
     *Trie search
     * will iterate through every character of key
     * and check if any children are null, which will
     * result in return false, otherwise if every character
     ******************************************************/
    @Override
    public String search(String key) {
        int currChar;
        int length = key.length();
        int index;
        String suggest = "";
        TrieNode curr = root;

        for(currChar = 0; currChar<length; currChar++) {
            index = key.charAt(currChar) - 'a';
            if(index == -58) { //apostrophe - 'a' = -58
                index = 26;
            }
            if(index < 0 || index > 26) { //if char is not a-z or ', then return false
                return "";
            }
            if (curr.children[index] == null) {
                return suggest = suggestWord(suggest, curr);
            }
            if(curr.children[index] != null && index == 26) {
                suggest = suggest+key.charAt(currChar);
                currChar--;
            }
            else {
                suggest = suggest + key.charAt(currChar);
            }

            curr = curr.children[index];

        }
        if (curr != null && curr.isEndOfWord) {
            return key;
        }
        else if(!key.equals("")) {
            assert curr != null;
            return suggest = suggestWord(suggest, curr);
        }
        return key;
    }

    private String suggestWord(String suggest, TrieNode current) {
        int i = 0;
        while(!current.isEndOfWord) {
            if (current.children[i] != null) {
                suggest = suggest + (char) (i + 97);
                current = current.children[i];
                i = 0;
            }
            else if(current.children[i] == null) {
                i++;
            }
        }
        return suggest;
    }
}
