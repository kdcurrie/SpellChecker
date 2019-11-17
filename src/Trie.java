import java.security.AlgorithmConstraints;

public class Trie {

    static final int ALPHABET_SIZE = 27;
    TrieNode root = new TrieNode();

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
    public void insert(String key) {
        int currChar;
        int length = key.length();
        int index;
        TrieNode curr = root;

        for(currChar = 0; currChar < length; currChar++)
        {
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

    /********************************************
     *Trie search
     * will iterate through every character of key
     * and check if any children are null, which will
     * result in return false, otherwise if every character
     ********************************************/
    public boolean search(String key) {
        int currChar;
        int length = key.length();
        int index;
        TrieNode curr = root;

        for(currChar = 0; currChar<length; currChar++)
        {
            index = key.charAt(currChar) - 'a';
            if(index == -58) { //apostrophe - 'a' = -58
                index = 26;
            }
            if(index < 0 || index > 26) { //if char is not a-z or ', then return false
                return false;
            }
            if(curr.children[index] == null) {
                return false;
            }
            curr = curr.children[index];
        }
        return(curr != null && curr.isEndOfWord);
    }
}
