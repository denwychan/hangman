import acm.util.*;
import java.io.*;
import java.util.*;

/**
 * This class implements the lexicon for use in the game of Hangman
 * @author denisechan
 */

public class HangmanLexicon {
	
	private ArrayList<String> secretWordList = new ArrayList<String>();
	
	/**This is the HangmanLexicon constructor*/
    public HangmanLexicon() {
    	//Open HangmanLexicon.txt file using a BufferedReader to read it line by line
    	BufferedReader rd = null; 
	    try {
	    	rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
	    	while (true) {
	    		//Read lines from the file into an ArrayList
	    		String secretWord = rd.readLine();
	    		if (secretWord == null) break; 
	    		secretWordList.add(secretWord);
	    	}
		    rd.close();
		// Warning if the file cannot be found
	    } catch (IOException ex) {
	    	System.out.println("Where's the file yo?");
	    }
    }
        
    /** Return the number of words in the lexicon. */
	public int getWordCount() {
		return secretWordList.size();
	}
	

	/** Get the secret word from the secret word array list. */	
	public String getWord(int index) {
		return secretWordList.get(index);
	}
	
}
