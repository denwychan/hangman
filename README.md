# Hangman

This is an implementation of the classic children's game [Hangman] (https://en.wikipedia.org/wiki/Hangman_(game))  
This is Assignment #4 of CS106A Programming Methodologies module by Stanford Univeristy

## How to Play

- The player must guess the randomly generated secret word by guessing 1 letter at a time
- To win, the player must guess the word the picture of the 'Hangman' is drawn fully on the canvas

### Rules

- Guesses are accepted in upper or lower case
- Guesses involving more than 1 letter is illegal and prompts new guess
- If the player guesses a correct letter (even more than once), no life is lost
- If the player guesses an incorrect letter (once or more than once), they lose a life

## Game Logic

- The Game selects random secret word 
- The Game prints out rows of dashes representing the number of letters in secret word 
- The Game asks the player to guess a letter at a time 
- If the player guesses the correct letter, the Game displays all instances of the letter in the correct position 
- If the guess is incorrect, the player loses a life 
- The game ends when the user guesses the correct word before they lose all 8 lives i.e. the player wins
- **OR** the game ends when the user loses all 8 lives and the 'Hangman is drawn' i.e. the player loses
- When the player makes an incorrect guess, a body part is drawn in the following order for the 'Hangman'
     - head
     - body
     - left arm
     - right arm
     - left leg
     - right leg
     - left foot
     - right foot
     - Note: the Scaffold and rope are drawn before game starts
     
## Software Requirements

Coming soon