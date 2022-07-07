import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;

/**
* This program plays a game of Hangman. The game randomly generates a secret word 
* and prompts the player to guess the letters. Each incorrect guess results in a 
* gradual "Hangman" drawing on the canvas, until the full picture of a man being 
* hung on a scaffold is rendered, which indicates that the player has lost
* the game by failing to guess the secret word in the number of allowed guesses.
* If the player guesses all the letters before "Hangman" is drawn, they win the game.
*/

public class Hangman extends ConsoleProgram {

    // Initialise the variable for guesses in a single game
	private int guesses;
	/* 
	 * Initialise an instance of the Hangman Lexicon to generate random words for 
	 * guessing 
	 */
	private HangmanLexicon lexicon = new HangmanLexicon();
	// Initialise the variable for the canvas
	private HangmanCanvas canvas;
	
	/*
	 * Add new Hangman canvas which displays the Hangman drawing and the word which 
	 * the player has to guess
	 */
	public void init() {
        canvas = new HangmanCanvas();
        add(canvas);
	}
	
	/* 
	 * Main run method for playing Hangman. Allow the player to keep play until they 
	 * specify that they do not want to play anymore
	 */
	public void run() {
    	while (true){
    		playHangman();
    		if (!playAgain()) {
    			break;
    		}
    	}
    	println("That's cool! See you next time :-)" );
    }

	/*
	 *  Play a single game of Hangman. Get the secret word and conceal it by hashes.
	 *  If the player guesses a letter correctly, the letter in the word is
	 *  unveiled. The player wins if they guess the secret word before their guesses
	 *  run out
	 */
	private void playHangman() {
		// Refresh the canvas for a new game
		canvas.reset();
		println("Welcome to Hangman!");
		// Set the number of guesses in a single game
		guesses = 8;
		String secretWord = getSecretWord();
    	String guessWord = constructGuessWord(secretWord);
    	showGuessWord(guessWord);
    	//Update the guess word after a correct guess
    	guessWord = guessSecretWord(guessWord, secretWord);
    	winOrLose(guessWord, secretWord);
	}
	
	// Get the secret word from the Lexicon
    private String getSecretWord() {
    	RandomGenerator randomWord = RandomGenerator.getInstance();  
    	return lexicon.getWord(randomWord.nextInt(lexicon.getWordCount()));
    }
    
    // Construct the guess word which is the secret word concealed by hashes
    private String constructGuessWord(String secretWord) {
    	String guessWord = "";
    	for (int i = 0; i < secretWord.length(); i++) {
    		guessWord += '-';
    	}
    	return guessWord;
    }
    
    /*
     *  Reveals the guess word after a guess. If the player correctly guesses 
     *  a letter, it is unveiled, otherwise the letter remain concealed by a
     *  hash
     */
    private void showGuessWord(String guessWord) {
    	canvas.displayWord(guessWord);
    	println("The word now looks like this: " + guessWord); 
    	println("You have " + guesses + " guesses left");
    }
    
	/*
	 *  If the player hasn't guess the secret word and they still have guesses,
	 *  allow them to keep guessing
	 */
    private String guessSecretWord(String guessWord, String secretWord){
    	while (!guessWord.equalsIgnoreCase(secretWord) && guesses > 0 ) {
    		guessWord = guessLetter(secretWord, guessWord);
    		showGuessWord(guessWord);
    	}	
    	return guessWord;
    }
    
    // Allow the player to guess a letter and update guess word where applicable
	private String guessLetter(String secretWord, String guessWord) {
		String letter = readLine("Your guess: ").toUpperCase();
		while ((letter.length() != 1) || letter.matches("[^A-Z]+")){ 
			println("This guess is illegal");
			letter = readLine("Your guess: ").toUpperCase();
		}
		if (secretWord.contains(letter)) {
			int index = secretWord.indexOf(letter);
			while (index > -1 ) {
				//update the guessWord with the letter at the index
				guessWord = guessWord.substring(0, index) + letter + guessWord.substring(index + 1);
				index = secretWord.indexOf(letter, index + 1);
			}
		}
		/* 
		 * If the letter is not in the guess word, deduct the number of guesses
		 * and let the player know
		 */
		else {
			guesses --;
			canvas.noteIncorrectGuess(letter.charAt(0));
			println("There are no " + letter + " in the word");
		}
		return guessWord; 
    }
   
	// Displays whether the player has won or lost the game
	private void winOrLose(String guessWord, String secretWord) {
		// If the play guesses the word, they win
		if (guessWord.equalsIgnoreCase(secretWord)) {
			println("You guessed the word: " + secretWord + ". You win!");
		// If the player has no more guesses, they lose
		} else {
			println ("You're completely hung. The word was: " + secretWord + ". You lose.");
		}
	}
	
	// Allow the player to chose whether they want to play again
	private boolean playAgain(){
		String playerResponse = readLine("Another game? Reponse \"Y\" or \"N\": ");
		while (true) {
			/* 
			 * 'Y' to continue and 'N' to quit the game. Display default warning if the player enters
			 * an invalid response
			 */
			switch(playerResponse.toUpperCase()) {
				case "Y": return true;
				case "N": return false;
				default: playerResponse = readLine("Invalid response. Respond \"Y\" or \"N\" only please: ");
			}
		}
	}
	
}
