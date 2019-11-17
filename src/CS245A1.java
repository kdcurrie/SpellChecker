import java.io.BufferedReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class CS245A1 {

    ArrayList<String> dictionary;
    ArrayList<String> inputWords;
    ArrayList<String> outputWords;
    SearchandInsert tree;
    SuggestWords reccWords;
    int size;
    long start;
    long end;

    public CS245A1 () {
        dictionary = new ArrayList<>();
        inputWords = new ArrayList<>();
        outputWords = new ArrayList<>();
        System.out.println("Starting up Spell Checker");
    }

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

    /**********************************
     * whichImplementation will run
     * whichever storage implementation
     * is in the config file
     **********************************/
    public void whichImplementation(String storageType) {
        if(storageType.equals("trie")) {
            System.out.println("configuration file calls for storage type: trie");
            start = System.currentTimeMillis();
            tree = new Trie();
            treeImplementation();
            end = System.currentTimeMillis();
            System.out.println("Trie implementation time: " + (end - start) + "ms");

        }
        else if(storageType.equals("tree")) {
            System.out.println("configuration file calls for storage type: tree");
            start = System.currentTimeMillis();
            tree = new BST();
            treeImplementation();
            end = System.currentTimeMillis();
            System.out.println("Tree implementation time: " + (end - start) + "ms");
        }
        else {
            System.out.println("No configuration provided, storage type will default to trie.");
            start = System.currentTimeMillis();
            tree = new Trie();
            treeImplementation();
            end = System.currentTimeMillis();
            System.out.println("Trie implementation time: " + (end - start) + "ms");
        }
    }

    /**********************************
     * Buffer Reader that reads English.0
     * converts everything to lowercase
     **********************************/
    public void readDictionary() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/english.0"))) {
            String line = reader.readLine();
            while(line != null) {
                if(line.length() >= 1){
                    dictionary.add(line.toLowerCase());
                }

                line = reader.readLine();
            }
//            System.out.println(dictionary.toString());
            size = dictionary.size();
            reader.close();

        } catch(IOException e) {
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

                String storageType = implementation.getProperty("storage");
                return storageType;

        } catch (IOException f) {
            f.printStackTrace();
            return null;
        }
    }
    /*******************************************
     * Read Input File Function
     * This function reads in each line of
     * input file into an Array List and removes
     * all non alphabetic and ' characters
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
//            System.out.println(inputWords.toString());

        } catch(IOException e) {
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

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void runCS245A1(String argument) {
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
