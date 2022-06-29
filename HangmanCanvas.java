import acm.graphics.*;

/**
 * This class keeps track of the Hangman display. It displays the secret word, 
 * the incorrectly guessed letters, and the advancing stages of the Hangman 
 * drawing as more and more incorrect letters are guessed.
 * @author denisechan
 */
public class HangmanCanvas extends GCanvas {
	
	//This is the y where the body starts 
	private int bodyPlacement  = ROPE_LENGTH + (2 * HEAD_RADIUS);
	//This is the y where the arm starts 
	private int armPlacement = ROPE_LENGTH + (2 * HEAD_RADIUS) + ARM_OFFSET_FROM_HEAD;
	//This is the y where the hip starts
	private int hipPlacement = ROPE_LENGTH + (2 * HEAD_RADIUS) + BODY_LENGTH;
	//This is the y where the foot starts
	private int footPlacement = ROPE_LENGTH + (2 * HEAD_RADIUS) + BODY_LENGTH + LEG_LENGTH;
	//Guess word label on the canvas
	private GLabel guessWordLabel; 
	//Label to keep track of incorrect letters guessed on the canvas
	private GLabel incorrectLettersGuessedLabel; 
	//Variable to track the letters that constitutes incorrect guesses by the user
	private String incorrectGuess = "";
	//Variable to track the number of incorrect guesses by the user
	private int numIncorrectGuesses = 0;
	
	/** 
	 * Resets the display so that only the scaffold, beam and guess word 
	 * label appears to start the game
	 */
	public void reset() {
		removeAll();
		numIncorrectGuesses = 0;
		incorrectGuess = "";
		addScaffold();
		addBeam();
		addGuessWordLabel();
		addIncorrectLettersGuessedLabel();
	}

	/**
	 * Updates the word on the screen according to the successful
	 * guesses by the player. The argument string shows what letters have
	 * been guessed so far; unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		guessWordLabel.setLabel(word);
	}

	/**
	 * Updates the display according to the incorrect guesses by the
	 * player. Calling this method causes the next body part to appear
	 * on the scaffold and adds the letter to the list of incorrect
	 * guesses that appears at the bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		numIncorrectGuesses ++;
		if (incorrectGuess.indexOf(letter) == -1) {
			incorrectGuess += letter;
		}
		incorrectLettersGuessedLabel.setLabel(incorrectGuess);
		// Add body parts in order according to the number of incorrect guesses
		switch(numIncorrectGuesses) {
			case 1: 
				addRope(); 
				break; 
			case 2: 
				addHead(); 
				break;
			case 3: 
				addBody(); 
				break;
			case 4: 
				addUpperArm(true); 
				addLowerArm(true); 
				break;
			case 5: 
				addUpperArm(false); 
				addLowerArm(false); 
				break;
			case 6: 
				addHip(); 
				break;
			case 7:
				addLeg(true); 
				addFoot(true); 
				break; 
			case 8: 
				addLeg(false); 
				addFoot(false); 
				break;
			default: 
				break;
		}
	}
	
	// The x starting point of the canvas is midway or 1/2
	private int getStartWidth() {
		return getWidth()/2;
	}
	
	// The y starting point of the canvas is 1/12 down from the top
	private int getStartHeight() {
		return getHeight()/12;
	}

	// Add the label for the guess word on the canvas
	private void addGuessWordLabel () {
		guessWordLabel = new GLabel("", getStartWidth()/2, getStartHeight() * 11 );
		add(guessWordLabel);
	}
	
	// Add the label for the incorrect letters guessed
	private void addIncorrectLettersGuessedLabel(){
		incorrectLettersGuessedLabel = new GLabel("", getStartWidth()/2, getStartHeight() * 11.5 );
		add(incorrectLettersGuessedLabel);
	}
	
	// Add the scaffold
	private void addScaffold() {
		GLine scaffold = new GLine(getStartWidth() - BEAM_LENGTH, getStartHeight(), 
									getStartWidth() - BEAM_LENGTH, SCAFFOLD_HEIGHT);
		add(scaffold);
	}
	
	// Add the beam
	private void addBeam() {
		GLine beam = new GLine(getStartWidth() - BEAM_LENGTH, getStartHeight(), 
								getStartWidth(), getStartHeight());
		add(beam);
	}
	
	// Add rope
	private void addRope() {
		GLine rope = new GLine(getStartWidth(), getStartHeight(),
								getStartWidth(), getStartHeight() + ROPE_LENGTH);
		add(rope);
	}
	
	// Add head
	private void addHead() {
		GOval head = new GOval((2 * HEAD_RADIUS), (2 * HEAD_RADIUS));
		add(head);
		head.move(getStartWidth() - HEAD_RADIUS, getStartHeight() + ROPE_LENGTH);
	}
	
	// Add body
	private void addBody(){
		GLine body = new GLine(getStartWidth(), getStartHeight() + bodyPlacement, 
								getStartWidth(), getStartHeight() + bodyPlacement + BODY_LENGTH);
		add(body);
	}
	
	// Add upper arm according to whether it is the left or right arm
	private void addUpperArm(boolean isLeft) {
		GLine upperArm = new GLine(0, getStartHeight() + armPlacement, 
									UPPER_ARM_LENGTH, getStartHeight() + armPlacement);
		add(upperArm);
		if (isLeft == true) {
			upperArm.move(getStartWidth() - UPPER_ARM_LENGTH, 0);
		} else {
			upperArm.move(getStartWidth(), 0);
		}
	}
	
	// Add lower arm according to whether it is the left or right arm
	private void addLowerArm(boolean isLeft) {
		GLine lowerArm = new GLine (0, getStartHeight() + armPlacement, 
									0, getStartHeight() + armPlacement + LOWER_ARM_LENGTH);
		add(lowerArm);
		if (isLeft == true) {
			lowerArm.move(getStartWidth() - UPPER_ARM_LENGTH, 0);
		} else {
			lowerArm.move(getStartWidth() + UPPER_ARM_LENGTH, 0);	
		}
	}
	
	// Add hip
	private GLine addHip() {
		GLine hip = new GLine(getStartWidth() - HIP_WIDTH, getStartHeight() + hipPlacement, 
								getStartWidth() + HIP_WIDTH, getStartHeight() + hipPlacement);
		add(hip);
		return hip;
	}
	
	// Add leg according to whether it is the left or right leg
	private void addLeg(boolean isLeft) {
		GLine leg = new GLine(0, getStartHeight() + hipPlacement,
								0, getStartHeight() + hipPlacement + LEG_LENGTH);
		add(leg);
		if (isLeft == true) {
			leg.move(getStartWidth() - HIP_WIDTH, 0);
		} else {
			leg.move(getStartWidth() + HIP_WIDTH, 0);
		}
	}
	
	// Add foot according to whether it is the left or right foot
	private void addFoot(boolean isLeft) {
		GLine foot = new GLine (0, getStartHeight() + footPlacement, 
								FOOT_LENGTH, getStartHeight() + footPlacement);
		add(foot);
		if (isLeft == true) {
			foot.move(getStartWidth() - HIP_WIDTH - FOOT_LENGTH, 0);
		} else {
			foot.move(getStartWidth() + HIP_WIDTH, 0);
		}
	}
	
	// Constants for the simple version of the Hangman picture (in pixels)
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
