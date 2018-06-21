import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Word_Operatoin_Class {

    //file from which data is read
   private String filePath ;

   //a list of words that do not contain other words
   private List<String> uniqueWords;
      //the maximum number of words contained in the given word to be checked	+
      private int maxConcatenatedInCheckedWord;

   //The indicator, or the given word now checking is unique or not
   private boolean isWordUnique;
    //the number of words containing the check word at the moment of the check
   private int concatenatedInCheckedWord;
    List<String> listss = new ArrayList<>();

    //counting words containing other words
    private int concatenatedWordCount;

    private List<String>[] concatenatedWordsWithMaxLendth;

    private String wordForCheckCutted;
    //number of the word that is being checked
    private long numberOfWordWhatChekedInThisMoment;

    String words;

    Word_Operatoin_Class(String filePath){
        this.filePath = filePath;
        words = new String();
        uniqueWords = new ArrayList<>();
        concatenatedInCheckedWord = 0;
        maxConcatenatedInCheckedWord = 0;
        concatenatedWordsWithMaxLendth = new List[2];
        for (int i = 0; i < concatenatedWordsWithMaxLendth.length;i++){
            concatenatedWordsWithMaxLendth[i] = new ArrayList<>();
            concatenatedWordsWithMaxLendth[i].add("");
        }
        concatenatedWordCount = 0;
        numberOfWordWhatChekedInThisMoment = 0;
    }


    //sort words that are in the file by their length,
    // creating a list in the list, the first number is the number of letters in words,
    // the second contains words with the appropriate number of letters.
    public List<ArrayList<String>> sortAllWordsInFileByLendth(){
        List<ArrayList<String>> sortedWordForLendrh;
        sortedWordForLendrh = new ArrayList<>();
        char[] bufferForReadCharFromFille = new char[64000];

        BufferedReader reader = null;
        try {
            int theLastCharInFile = 0;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

            while ((theLastCharInFile = reader.read(bufferForReadCharFromFille)) > 0) {
                System.out.print("Read Some Chars \n");
                //We pass only on filled arrays, null elements do not move
                for (int numberOfReadedCharsFromFile = 0; numberOfReadedCharsFromFile < theLastCharInFile; numberOfReadedCharsFromFile++) {

                    char oneSumbolFromWord = bufferForReadCharFromFille[numberOfReadedCharsFromFile];

                    //check the symbol for a retreat,
                    // if we do not add a word to the word, otherwise we will put the word in the list of sorted words
                    if ((int) oneSumbolFromWord == 13 ) {
                        //check if there is a place for the word with such number of words in the list.
                        //if not, we create it
                        if (sortedWordForLendrh.size() < words.length()) {
                            for (int i = sortedWordForLendrh.size(); i < words.length(); i++) {
                                sortedWordForLendrh.add(new ArrayList<>());
                            }
                        }
                        //add the word to the list
                        if(words.length() >0) {
                            ArrayList<String> timeArrayList = sortedWordForLendrh.get(words.length() - 1);
                            timeArrayList.add(words);
                        }
                        words = "";
                    } else if ((int) oneSumbolFromWord != 10) {
                        words += oneSumbolFromWord;
                    }
                }
            }
        }catch (IOException e){
            System.out.print("Not File");
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.print("End Sort");
        return sortedWordForLendrh;
    }

    //the methodology first sorts the data from the file
    // in the proper order for itself, then checks each word
    // for uniqueness or concatenated of word.

    public void checkAllWordsFromList(){
        List<ArrayList<String>> sortedWordsByLendth;
        //Sort words by length
        sortedWordsByLendth = sortAllWordsInFileByLendth();
        for(ArrayList<String> arrayList : sortedWordsByLendth){
            for (String s: arrayList){
                checkWordForConcatenated(s);
            }
        }
    }

    public List<String>[] getConcatenatedWordsWithMaxLendth() {
        return concatenatedWordsWithMaxLendth;
    }

    //The method passes the word given to him to check
    // for the uniqueness or content of the unique words,
    // and from the data it puts the word into a unique or
    // array of words with the highest lendth of word.
    public void checkWordForConcatenated(String wordForCheck){
        numberOfWordWhatChekedInThisMoment++;
        isWordUnique = true;
        maxConcatenatedInCheckedWord = 0;
        concatenatedInCheckedWord = 0;
        System.out.print(wordForCheck + " : " + numberOfWordWhatChekedInThisMoment + "\n");

        //Calling the method to get the data is unique or not
        methodForRecurciveCheckWord(wordForCheck);

        //If the word contains other words,
        // then place it in misleading words containing
        // other words according to the they lendth
        if(!isWordUnique){

            concatenatedWordCount++;
            wordForCheck = wordForCheck + ": Has Words:" + maxConcatenatedInCheckedWord;

            for(int positionInConcatenateWords = 0; positionInConcatenateWords < concatenatedWordsWithMaxLendth.length; positionInConcatenateWords++){
                    if (wordForCheck.length() == concatenatedWordsWithMaxLendth[positionInConcatenateWords].get(0).length()) {
                        concatenatedWordsWithMaxLendth[positionInConcatenateWords].add(wordForCheck);
                        break;
                    }else if(wordForCheck.length() > concatenatedWordsWithMaxLendth[positionInConcatenateWords].get(0).length()){
                        for(int secondPositionInMassive = concatenatedWordsWithMaxLendth.length-1; secondPositionInMassive > positionInConcatenateWords; secondPositionInMassive--){
                           concatenatedWordsWithMaxLendth[secondPositionInMassive].clear();
                           for(String word : concatenatedWordsWithMaxLendth[secondPositionInMassive - 1])
                            concatenatedWordsWithMaxLendth[secondPositionInMassive].add(word);
                        }
                        concatenatedWordsWithMaxLendth[positionInConcatenateWords].clear();
                        concatenatedWordsWithMaxLendth[positionInConcatenateWords].add(wordForCheck);
                        break;
                    }

            }
        //If the word is unique, we add it to the list of unique words
        }else if(isWordUnique){
            uniqueWords.add(wordForCheck);
        }
    }

    //Checks the word with all the unique words,
    // when the words are found in the drowned word,
    // he takes away part of the word and begins to search from the beginning.
    public void methodForRecurciveCheckWord(String wordForCheck){

        wordForCheckCutted = wordForCheck;

        if(!wordForCheck.equals(""))
            //We go through all the unique words
        for(String uniqueWord : uniqueWords){
            //The word can not contain a word more than itself,
            // so if a unique word is greater than ours at the moment of the check,
            // we stop this current loop, as all subsequent words will also be larger than our word.
            if(wordForCheck.length() < uniqueWord.length()){
                break;
            }
            //Allocate the word from the checking length with a unique word
            String wordForCheckWithUniqueLenth = wordForCheck.substring(0,uniqueWord.length());
            //Compare the unique word and the received word from our checking word with the length of a unique word
            if(wordForCheckWithUniqueLenth.equals(uniqueWord)){
                //If the words coincide, we cast a part that coincided with our word
                wordForCheckCutted = wordForCheck.substring(uniqueWord.length());

                if(isWordUnique) {
                    //We increase the word of every word that holds our word
                    concatenatedInCheckedWord++;
                    //run the recursion of the method with the word left of ours, without the matching part

                    methodForRecurciveCheckWord(wordForCheckCutted);
                }
            }
        }
        //If words in our word are over characters, then they have put one of the combinations of unique words
        if(wordForCheck.equals("") ){
            //checks for similar words, since two identical words contain each other
            if(concatenatedInCheckedWord > 1){
                //mark our word not unique
                isWordUnique = false;
            }
            //We check whether this combination of unique words that can contain the word is greatest	+
            if(concatenatedInCheckedWord > maxConcatenatedInCheckedWord){
                maxConcatenatedInCheckedWord = concatenatedInCheckedWord;
            }

        }

        if(concatenatedInCheckedWord > 0)
        concatenatedInCheckedWord--;
    }

    public int getConcatenatedWordCount() {
        return concatenatedWordCount;
    }

    public List<String> getUniqueWords() {
        return uniqueWords;
    }

}
