import java.util.List;

public class Test {
    public static void main(String[] args){



        //create class for do operartion with words in File
        Word_Operatoin_Class wordOperationClass= new Word_Operatoin_Class("D://1.txt");



        //Search concatenated Word
        wordOperationClass.checkAllWordsFromList();

        //Obtaining word data that contains other words
        List<String>[] concatenatedWords = wordOperationClass.getConcatenatedWordsWithMaxLendth();
        int concatenatedWorldCount = wordOperationClass.getConcatenatedWordCount();
        for(int i = 0; i < 2; i ++) {
            String []word = concatenatedWords[i].get(0).split(":");
            System.out.println("longest " + (i+1) + " :" + concatenatedWords[i] + "\ncountWordsInWord: " + word[0].length());
        }
        System.out.println(concatenatedWorldCount);

    }
}
