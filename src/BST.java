import java.util.Stack;

public class BST implements SearchAndInsert {

    private Node root = null;

    /******************************
     *Node Class & Node Constructor
     ******************************/
    class Node {
        String data;
        Node left = null;
        Node right = null;

        Node(String word) {
            data = word;
            left = null;
            right = null;
        }
    }
    /****************************************
     *BST search
     * Searches for word at the root and then checking each child node.
     *This function also serves as the BST suggest alternative word
     * function by making lexicographic comparisons and providing a suggestion
     * closest to where the word should be located in the tree as a node
     ***************************************/
   @Override
    public String search(String word) {
        return search(word, root);
    }
    private String search(String word, Node root){
        if(word.length() == 0) {
            return "";
        }
        while (root != null) {
            if(root.left == null && root.right == null) {
                return root.data;
            }
            else if ((root.data.compareTo(word) > 0) && root.left == null) {
                return root.data;
            }
            else if ((root.data.compareTo(word) < 0) && root.right == null) {
                return root.data;
            }
            else if(root.data.compareTo(word) > 0)
                root = root.left;
            else if (root.data.compareTo(word) < 0)
                root = root.right;
            else
                return root.data;
        }
        return root.data;
    }

        /**************************************************************
         *Will traverse the tree and find the parent node of
         * the word by comparing them lexographically, and then insert
         * the word into the correct node
         **************************************************************/
        @Override
        public void insert(String word) {
            root = insertIterative(word, root);
        }

        private Node insertIterative(String word, Node root) {
            Node curr = root;
            Node parent = null; //node to store parent of current

            if (root == null) { // if tree is empty
                return new Node(word);
            }
            while (curr != null) { // traverse the tree and find parent node of word
                parent = curr; //update parent node to the current node
                // String currStr = curr.data.replace("'","");
                if (curr.data.compareTo(word) < 0) {
                    curr = curr.right;
                }
                else {
                    curr = curr.left;
                }
            }
            //once parent node is found, it will create new child node with word value
            if (parent.data.compareTo(word) < 0) {
                parent.right = new Node(word);
            }
            else {
                parent.left = new Node(word);
            }
            return root;
        }

        /**********************************
         *prints out entire tree in
         * alphabetical order
         **********************************/
//        public void iterativePreOrder()
//        {
//            iterativePreOrder(root);
//        }
//        private void iterativePreOrder(Node node) {
//
//            // Base Case
//            if (node == null) {
//                return;
//            }
//            // Create an empty stack and push root to it
//            Stack<Node> nodeStack = new Stack<Node>();
//            nodeStack.push(root);
//            int count = 0;
//            while (!nodeStack.empty()) {
//
//                // Pop the top of stack and print
//                Node myNode = nodeStack.peek();
//                nodeStack.pop();
//                System.out.print(myNode.data + " , ");
//                System.out.println(++count);
//
//                // Push right and left children to stack
//                if (myNode.right != null) {
//                    nodeStack.push(myNode.right);
//                }
//                if (myNode.left != null) {
//                    nodeStack.push(myNode.left);
//                }
//            }
}