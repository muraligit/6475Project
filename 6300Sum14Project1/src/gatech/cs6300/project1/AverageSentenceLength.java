package gatech.cs6300.project1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class AverageSentenceLength {

    private String path;

    private int minWordSize = 3;
    //TODO sufficient defaults?
    private String sentenceDelimiters = "[\\.\\?!]";
    private String wordDelimiters = "[\\,\\:\\s]";

    public AverageSentenceLength(String pathCL){
    	this.path=pathCL;
    }

    private int countWords(String sentence) {
        Scanner wordScanner = new Scanner(sentence).useDelimiter("[ ]+");
        int wordCount = 0;
        try {
            while(wordScanner.hasNext()) {
            	String word = wordScanner.next();
            	word = word.replaceAll(wordDelimiters, "");
            	if(word.length() > minWordSize) {
            		wordCount++;
            	}
            }
            return wordCount;
        } finally {
            if(wordScanner != null) wordScanner.close();
        }
        
    }

   
    public void sizeIt() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path)).useDelimiter(sentenceDelimiters);
        try {
            int scount = 0;
            int total = 0;
            while(scanner.hasNext()) {
                String sentence = scanner.next();
               
                int wordcount=countWords(sentence);
                // TODO ignore sentences with zero word counts
                if(wordcount>0){
                	scount++;
                	total += wordcount;
                }
                
            }
            //TODO division by zero
            System.out.println("Avg: "+(total/scount));
            
        }
        finally {
            if(scanner != null) scanner.close();
        }
    }

    public int getMinWordSize() {
		return minWordSize;
	}
	public void setMinWordSize(int minWordSize) {
		this.minWordSize = minWordSize;
	}
	public String getSentenceDelimiters() {
		return sentenceDelimiters;
	}
	public void setSentenceDelimiters(String sentenceDelimiters) {
		this.sentenceDelimiters = sentenceDelimiters;
	}
	public String getWordDelimiters() {
		return wordDelimiters;
	}
	public void setWordDelimiters(String wordDelimiters) {
		this.wordDelimiters = wordDelimiters;
	}      

}