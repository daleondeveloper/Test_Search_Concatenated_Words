import java.util.List;

public class Test {
    public static void main(String[] args){



        //create class for do operartion with words in File
        Word_Operatoin_Class wordOperationClass= new Word_Operatoin_Class("D://1.txt");



        //Search concatenated Word
        wordOperationClass.checkAllWordsFromList();

        //Obtaining word data that contains other words
        List<String>[] concatenatedWords = wordOperationClass.getConcatenatedWords();
        int[] countWordsInConcatenatedWords = wordOperationClass.getCountHowManyWordsIsInCheckedWord();
        int concatenatedWorldCount = wordOperationClass.getConcatenatedWordCount();
        for(int i = 0; i < 2; i ++) {
            System.out.println("longest " + i+1 + " :" + concatenatedWords[i] + "\ncountWordsInWord: " + countWordsInConcatenatedWords[i]);
        }
        System.out.println(concatenatedWorldCount);
    }
}
