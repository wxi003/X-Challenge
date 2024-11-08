/* This class represents the records that will be stored in the HashDictionary. Each record stored in
the dictionary will consist of two parts: a configuration and an integer score. */
public class Data{
    private String configuration;
    private int score1;
    public Data(String config, int score){
        configuration = config;
        score1 = score;
    }

    /*Returns the configuration stored in this Data object.*/
    public String getConfiguration(){
        return configuration;
    }

    /*Returns the score stored in this Data object.*/
    public int getScore(){
        return score1;
    }
}
