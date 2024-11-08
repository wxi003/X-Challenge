/* This class uses a hash table with separate chaining to implements the Dictionary ADT */
import java.util.LinkedList;

public class HashDictionary implements DictionaryADT {
    private int capacity; // size of the table
    private LinkedList<Data>[] table; // The array of linked list

    // constructor: returns an empty dictionary of the specified size
    public HashDictionary(int size){
        capacity = size;
        table = new LinkedList[capacity];

        // creates an empty table of the specified size
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // adds a new record to the dictionary
    public int put (Data record) throws DictionaryException{
        String config = record.getConfiguration();
        int hash = h(config);
        int collision = 0;

        LinkedList<Data> linkedList = table[hash];
        for (Data data : linkedList) {

            // if a record is already in the dictionary, we throw a DictionaryException
            if(data.getConfiguration().equals(config)){
                throw new DictionaryException();
            }
        }

        // to check collisions
        // counter++ if the linkedList already has at least one element
        // if the linkedList is empty, do nothing
        if (!linkedList.isEmpty()) {
            collision=1;
        }

        linkedList.add(record); // add it to the dictionary

        return collision;

    }

    // removes the record with the given config from the dictionary
    public void remove (String config) throws DictionaryException{
        int hash  = h(config);
        LinkedList<Data> linkedList =table[hash];

        for (Data data : linkedList) {
            // if a record is already in the dictionary, we throw a DictionaryException
            if(data.getConfiguration().equals(config)){
                linkedList.remove(data);
                return;
            }

        }
        throw new DictionaryException(); // throws a DictionaryException if no record stores config
    }

    // returns the score stored in the record of the dictionary
    // with key config, or -1 if config is not in the dictionary
    public int get (String config){
        int hash  = h(config);
        LinkedList<Data> linkedList =table[hash];

        for (Data data : linkedList) {
            // if config is in the dictionary, we return its score
            if(data.getConfiguration().equals(config)){
                return data.getScore();
            }

        }

        return -1; // config is not in the dictionary
    }

    // returns the number of Data objects stored in the dictionary
    public int numRecords(){
        int numData = 0;
        for (LinkedList<Data> linkedList: table) {
            numData += linkedList.size();
        }
        return  numData;
    }

    // hash function
    private int h(String config){
        int x = 41;
        int m = 13339; // a prime number and the modulus used for the hash function
        int hash = m;

        for (char c: config.toCharArray()) {
            hash = hash *x +c;
        }

        hash %= m;
        hash = hash>0?hash:-hash;
        return hash;

    }
}
