import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

public class CS245A1 {

    private ArrayList<String> dictionary;
    private ArrayList<String> inputWords;
    private ArrayList<String> outputWords;
    private SearchAndInsert tree;
    private int size;

    public CS245A1 () {
        dictionary = new ArrayList<>();
        inputWords = new ArrayList<>();
        outputWords = new ArrayList<>();
    }
    /*****************************************
     * Inserts each word form the dictionary into
     * either tree or trie implementation
     *****************************************/
    public void treeImplementation() {
        try {
            for (int i = 0; i < size; i++) {
                tree.insert(dictionary.get(i));
            }
        }
        catch (Exception e) {
            System.out.println("\nFailed to insert dictionary.");
        }
    }

    /****************************************************************
     * whichImplementation will run whichever storage implementation
     * ia specified in the config file, or it will default to trie.
     * run-times for each implementation will be printed.
     ****************************************************************/
    public void whichImplementation(String storageType) {
        long start;
        long end;

        if (storageType.equals("tree")) {
            System.out.println("configuration file calls for storage type: tree");
            start = System.currentTimeMillis();
            tree = new BST();
            treeImplementation();
            end = System.currentTimeMillis();
            System.out.println("Tree implementation time: " + (end - start) + "ms");
        }
        else {
            if (!storageType.equals("trie")) {
                System.out.println("No configuration provided, storage type will default to trie.");
            }
            else {
                System.out.println("configuration file calls for storage type: trie");
            }
            start = System.currentTimeMillis();
            tree = new Trie();
            treeImplementation();
            end = System.currentTimeMillis();
            System.out.println("Trie implementation time: " + (end - start) + "ms");
        }
    }

    /**************************************************
     * Buffer Reader that reads English.0 from Github
     * and stores in Array List.
     * converts everything to lowercase.
     **************************************************/
    public void readDictionary() {
        System.out.println("Reading \"english.0\" dictionary file from github.");
        try {
            URL url = new URL("https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = in.readLine();
            while(line != null) {
                if(line.length() >= 1){
                    dictionary.add(line.toLowerCase());
                }

                line = in.readLine();
            }
            size = dictionary.size();
            in.close();

        } catch(IOException e) {
            System.out.println("Failed to read english.0 from github.");
            e.printStackTrace();
        }
    }

    /****************************************
     * Buffer Reader that loads property file
     ***************************************/
    public String readPropertyFile() {
        Properties implementation = new Properties();

        try (BufferedReader buffer = Files.newBufferedReader(Paths.get("src/a1properties.txt"))) {
            implementation.load(buffer);
            buffer.close();
            return implementation.getProperty("storage");

        } catch (IOException f) {
            f.printStackTrace();
            return null;
        }
    }
    /*******************************************
     * Read Input File Function
     * This function reads in each line of
     * input file into an Array List and removes
     * all non alphabetic and ' characters.
     * takes command line argument for file name.
     ******************************************/
    public void readInput(String inputFileName) {
        try (BufferedReader input = Files.newBufferedReader(Paths.get("src/" + inputFileName))) {
            String line = input.readLine();
            while(line != null) {
                if(line.length() >= 1){
                    line = line.replaceAll("[^a-zA-Z']", "");
                    inputWords.add(line.toLowerCase());
                }
                line = input.readLine();
            }
            input.close();
            System.out.println("\nInput file " + "\"" + inputFileName + "\"" + " read successfully.\n");

        } catch(IOException e) {
            System.out.println("Failed to read input file.");
            e.printStackTrace();
        }
    }
    /***********************************************************
     * Write Output file
     * Removes any lines where input provides no suggested words
     * For example:
     * will not provide suggested word for input of all numbers
     **********************************************************/
    public void writeOutput() {
        for(String line: inputWords) {
            outputWords.add(tree.search(line));
        }
        outputWords.removeIf(n->(n.length() == 0));
        try {
            FileWriter fw = new FileWriter("src/output.txt");
            BufferedWriter outputWriter = new BufferedWriter(fw);
            for(String line: outputWords) {
                outputWriter.write(line);
                outputWriter.newLine();
            }
            outputWriter.close();
            System.out.println("\nOutput file \"output.txt\" written too successfully.");

        } catch(IOException e) {
            System.out.println("Failed to write to \"output.txt\"");
            e.printStackTrace();
        }
    }

    public void runCS245A1(String argument) {
        System.out.println("Starting up Spell Checker...");
        readDictionary();
        readInput(argument);
        whichImplementation(readPropertyFile());
        writeOutput();
    }

    public static void main(String[] args) {
        String argument = null;
        if(args.length < 1) {
            System.out.println("No input file specified, will default to 'input.txt'");
            argument = "input.txt";
        }
        else {
            argument = args[0];
        }
        CS245A1 test = new CS245A1();
        test.runCS245A1(argument);
    }
}
