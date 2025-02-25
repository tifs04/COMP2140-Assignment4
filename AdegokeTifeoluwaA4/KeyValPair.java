/*
 * This class represents a key-value value pair where the key is the word and the value is its frequency.
 */
public class KeyValPair {
    String word;
    int frequency;

    public KeyValPair(String word, int frequency){
        this.word = word;
        this.frequency = frequency;
    }

    public String getString(){
        return this.word;
    }
}
