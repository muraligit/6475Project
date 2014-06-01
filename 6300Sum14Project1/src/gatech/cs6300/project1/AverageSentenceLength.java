package gatech.cs6300.project1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class AverageSentenceLength {

	private int smallWordCount;
	private String delimiterList, fileContents;
	private String[] wordArray;
	private File filename;
	
	/** 
	 *  Default Constructor
	 */
	public AverageSentenceLength()
	{
		smallWordCount = 3;
		delimiterList = ".?!";
		filename = null;
		fileContents = "";
	}
	
	/**
	 * Overloaded Constructor
	 */
	public AverageSentenceLength(int count, String delimeters, File file)
	{
		smallWordCount = count;
		delimiterList = delimeters;
		filename = file;
		fileContents = "";
	}
	
	/**
	 * Mutator for Filename
	 * 
	 * @param file A new filename
	 */
	public void setFile(File file)
	{
		filename = file;
	}
	
	/**
	 * Mutator for Delimiters
	 * 
	 * @param delimiter A new list of delimiters
	 */
	public void setSentenceDelimiters(String delimiter)
	{
		delimiterList = delimiter;
	}
	
	/** 
	 * Mutator for Minimum Word Length
	 * 
	 * @param min A new minimum word length
	 */
	public void setMinWordLength(int min)
	{
		smallWordCount = min;
	}
	
	/** 
	 * A method to report errors to the user
	 * 
	 * @param errorString the message to be relayed to the user
	 */
	public void errorReport(String errorString)
	{
		System.out.println("/n"+errorString+"/n");
		System.exit(0);
	}
	
	/**
	 * Input from a file 
	 */
	public void inputFile()
	{
		Scanner scanner = null;
		if (filename == null)
			errorReport("No filename given.");
		try
		{
			scanner = new Scanner (filename);
		} 
		catch (FileNotFoundException fnf)
		{
			errorReport("File not found: " + filename.getName());
		}
		fileContents = "";
		while (scanner.hasNext())
		{
			System.out.println(filename.getName());
			fileContents += scanner.next();
			//if there's not a space at the end of the line, add a space.
			if (fileContents.charAt(fileContents.length()-1)!=' ')
				fileContents += " ";
		}
		scanner.close();
		System.out.println(fileContents);
	}
	
	/**
	 * Find the number of words (ignoring size) for a file.
	 * 
	 * @return number of words of length greater than zero.
	 */
	public int findRawSize()
	{
		wordArray = fileContents.split("\\s+");  //stupid regex
		return wordArray.length;
	}
	
	/**
	 * Find the number of words larger than wordSize for a file.
	 * 
	 * @return number of words of length greater than wordSize
	 */
	public int computeAverageSentenceLength()
	{
		int wordCount = 0, sentenceCount = 0;
		for (int i = 0; i < findRawSize(); i ++)
		{
			if (wordArray[i].length() > smallWordCount)
				wordCount ++;
			for (int j = 0; j < delimiterList.length(); j ++)
				if (wordArray[i].indexOf(delimiterList.charAt(j))!=-1)
					sentenceCount++;
			System.out.println(i+" "+wordArray[i]);
		}
		if (sentenceCount != 0)
			return (int)(Math.round(wordCount / sentenceCount));
		return 0; //bad data
	}
}


