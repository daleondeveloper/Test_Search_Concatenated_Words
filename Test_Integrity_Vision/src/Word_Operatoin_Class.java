import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Word_Operatoin_Class {

    //file from which data is read
   private String filePath ;

   //a list of words that do not contain other words
   private List<String> uniqueWords;

   //The indicator, or the given word now checking is unique or not
   private boolean isWordUnique;
    //the number of words containing the check word at the moment of the check
   private int concatenatedInCheckedWord;
    //the maximum number of words contained in the given word to be checked
    private int maxConcatenatedInCheckedWord;

    //counting words containing other words
    private int concatenatedWordCount;

    private List<String> [] concatenatedWords;
    private int [] countHowManyWordsIsInCheckedWord;
    private String wordForCheckCutted;
    private long numberOfWordWhatChekedInThisMoment;

    String words;

    Word_Operatoin_Class(String filePath){
        this.filePath = filePath;
        words = new String();
        uniqueWords = new ArrayList<>();
        concatenatedInCheckedWord = 0;
        concatenatedWords = new List[2];
        countHowManyWordsIsInCheckedWord = new int[concatenatedWords.length];
        for (int i = 0; i < concatenatedWords.length;i++){
            concatenatedWords[i]=new ArrayList<>();
            countHowManyWordsIsInCheckedWord[i] = 0;
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
    // for uniqueness or how many unique words it contains

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

    //The method passes the word given to him to check
    // for the uniqueness or content of the unique words,
    // and from the data it puts the word into a unique or
    // array of words with the highest content of unique words.
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
        // other words according to the number of words contained therein.
        if(!isWordUnique){
            concatenatedWordCount++;

            for(int positionInConcatenateWords = 0; positionInConcatenateWords < concatenatedWords.length; positionInConcatenateWords++){
                //If the number of words is equal to one of the positions, we add to our word list in this position.
                if(maxConcatenatedInCheckedWord == countHowManyWordsIsInCheckedWord[positionInConcatenateWords]){
                    concatenatedWords[positionInConcatenateWords].add(wordForCheck);
                    break;
                    //If the number of words contained is greater than the position,
                    // we move all below the standing position below and put the word in its position.
                } else if(maxConcatenatedInCheckedWord > countHowManyWordsIsInCheckedWord[positionInConcatenateWords]){
                    for(int secondPositionInMassive = concatenatedWords.length-1; secondPositionInMassive > positionInConcatenateWords+1; secondPositionInMassive--){
                        concatenatedWords[secondPositionInMassive] = concatenatedWords[secondPositionInMassive - 1];
                        countHowManyWordsIsInCheckedWord[secondPositionInMassive] = countHowManyWordsIsInCheckedWord[secondPositionInMassive - 1];
                    }
                    concatenatedWords[positionInConcatenateWords].clear();
                    concatenatedWords[positionInConcatenateWords].add(wordForCheck);
                    countHowManyWordsIsInCheckedWord[positionInConcatenateWords] = maxConcatenatedInCheckedWord;
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
        if(wordForCheckCutted.equals("") ){
            //checks for similar words, since two identical words contain each other
            if(concatenatedInCheckedWord > 1){
                //mark our word not unique
                isWordUnique = false;
            }
            //We check whether this combination of unique words that can contain the word is greatest
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

    public List<String>[] getConcatenatedWords() {
        return concatenatedWords;
    }

    public int[] getCountHowManyWordsIsInCheckedWord() {
        return countHowManyWordsIsInCheckedWord;
    }

    public List<String> getUniqueWords() {
        return uniqueWords;
    }

}
