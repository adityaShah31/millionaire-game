import Game.*;
import Lifeline.*;
import Exception.InputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Launch {

    final static ArrayList<String> ANSWER_CHOICE_INDICES = new ArrayList<String>(Arrays.asList("A", "B", "C", "D"));

    public static void main(String[] args) {
        String playerName, menuChoice;
        boolean playerWalkedAway = false, playerAnswerIncorrect = false;
        int playerWinnings = 0;
        String currentInput;
        boolean goBackToMenu;

        Scanner read = new Scanner(System.in);

        System.out.println("Welcome to WHO WANTS TO BE A MILLIONAIRE?!");
        System.out.println("------------------------------------------");

        do {
            boolean isMenuChoiceValid;
            displayMenuPage();
            System.out.println("Enter your choice: ");
            menuChoice = read.nextLine();
            do {
                try {
                    if (menuChoice.length() != 1)
                        throw new Exception("Input should be SINGLE digit: ");

                    if (!menuChoice.equals("1") && !menuChoice.equals("2") && !menuChoice.equals("3"))
                        throw new Exception("Please enter valid input between  1 | 2 | 3  only :");

                    isMenuChoiceValid = true;
                } catch (Exception ex) {
                    isMenuChoiceValid = false;
                    System.out.println(ex.getMessage());
                    menuChoice = read.nextLine();
                }
            } while (!isMenuChoiceValid);

            switch (Integer.parseInt(menuChoice)) {
                case 1: //Start Game
                    goBackToMenu = false;

                    System.out.println("Enter your name here: ");
                    playerName = read.nextLine();
                    Player player = new Player(playerName);

                    System.out.println(playerName.toUpperCase() + ", would you like to play in EASY or HARD mode?\nEnter 'e' for EASY & 'h' for HARD: ");
                    String gameMode = read.nextLine();
                    boolean isModeInputValid;
                    do {
                        try {
                            if (gameMode.length() != 1)
                                throw new Exception("Enter SINGLE character inputs only: ");
                            if (!gameMode.equalsIgnoreCase("E") && !gameMode.equalsIgnoreCase("H"))
                                throw new Exception("Enter valid input between 'e' and 'h' only: ");

                            isModeInputValid = true;
                        } catch (Exception ex) {
                            isModeInputValid = false;
                            System.out.println(ex.getMessage());
                            gameMode = read.nextLine();
                        }
                    }
                    while (!isModeInputValid);

                    //at this point input will either be "E" or "H"
                    ArrayList<Question> questionBank;
                    int roundNumber = 1;
                    int questionNumber = 1;
                    int questionDollarValue;
                    boolean isEasyMode = gameMode.equalsIgnoreCase("E");

                    Game game = new Game(isEasyMode);
                    int questionsPerRound = isEasyMode ?
                            game.getMAX_QUESTIONS_EASY() / game.getNO_OF_ROUNDS() :
                            game.getMAX_QUESTIONS_HARD() / game.getNO_OF_ROUNDS();
                    questionBank = game.getGameQuestions(); //this function will automatically returns us randomized questions
                    // with the correct amount (9 for easy and 15 for hard, already sorted difficulty wise based on
                    // round number)
                    System.out.println("Alright " + playerName.toUpperCase() + ", let's begin!");

                    for (int i = 0; i < questionBank.size(); i++) {
                        if (!playerWalkedAway && !playerAnswerIncorrect) {

                            boolean isInputValid;

                            //Logic for end of each round "Walk Away?"
                            if ((i % questionsPerRound == 0) && (i != (questionBank.size() - 1)) && i != 0) { //will
                                // enter loop after every 3 questions for Easy and every 5 questions for Hard mode,
                                // except for the last iteration/question and the very first iteration/question
                                roundNumber += 1;
                                questionNumber = 1;
                                System.out.println("Alright, you now have a choice to either continue the game to" +
                                        " the next round or WALK AWAY with $" + playerWinnings + "\nWhat will it " +
                                        "be? Enter 'y' to CONTINUE GAME and 'n' to WALK AWAY:");
                                currentInput = read.nextLine();
                                do {
                                    try {
                                        isInputValid = InputException.validateYesNoInput(currentInput);
                                    } catch (InputException ix) {
                                        isInputValid = false;
                                        System.out.println(ix.getMessage());
                                        currentInput = read.nextLine();
                                    }
                                }
                                while (!isInputValid);


                                //at this point input is valid and either 'y' or 'n'
                                if (currentInput.equalsIgnoreCase("n")) {
                                    playerWalkedAway = true;
                                    System.out.println("No worries! Hey you played well today...");
                                    break;
                                }
                            }

                            questionDollarValue = game.getDollarValueForQuestion(i); //automatically returns correct
                            // dollar value based on game mode
                            System.out.println("ROUND " + roundNumber + ": Question " + questionNumber + " (for $" + questionDollarValue + ")");
                            questionNumber += 1;
                            Question question = questionBank.get(i);
                            displayQuestion(question, player);

                            //logic for locking Lifelines in hard mode, round 1
                            if (!isEasyMode && roundNumber == 1) {
                                System.out.println("**********");
                                System.out.println("Available Lifelines: \nLOCKED till next round");
                                System.out.println("**********");
                                System.out.println("Enter your answer (A | B | C | D): ");

                                currentInput = read.nextLine();
                                do {
                                    try {
                                        isInputValid = InputException.validatePlayerChoiceInput(question, 0,
                                                currentInput);
                                    } catch (InputException exp) {
                                        System.out.println(exp.getMessage());
                                        isInputValid = false;
                                        currentInput = read.nextLine();
                                    }
                                }
                                while (!isInputValid);

                            } else { //allowed inputs are [a,b,c,d] + [1,2,3] and only valid ones i.e if lifeline
                                // already used, valid inputs will be less
                                displayLifelines(player);

                                currentInput = read.nextLine();
                                do {
                                    try {
                                        isInputValid = InputException.validatePlayerChoiceInput(question, player.getLifelines().size()
                                                , currentInput);

                                        //at this point we know that the input is valid, i.e. anything from [a,b,c,d,1,2,3]

                                        //logic for if the player wants to use multiple lifelines on a single question
                                        while (player.getLifelines().size() != 0 && isValidLifelineInput(currentInput)) {
                                            //if player choses to use lifeline
                                            //if (isValidLifelineInput(currentInput)) {
                                            int currentLifelineIndex = Integer.parseInt(currentInput) - 1;//since indexes start from 0,1,2,...

                                            Lifeline chosenLifeline = player.getLifelines().get(currentLifelineIndex);
                                            player.useLifeline(chosenLifeline, question); //the question object is modified
                                            // inside this function

                                            System.out.println();
                                            System.out.println("Alright, you've used your lifeline. Here's the updated question: ");
                                            //print updated question
                                            displayQuestion(question, player);
                                            displayLifelines(player);

                                            currentInput = read.nextLine();
                                            do {
                                                try {
                                                    isInputValid = InputException.validatePlayerChoiceInput(question,
                                                            player.getLifelines().size(),
                                                            currentInput);
                                                } catch (InputException exp) {
                                                    System.out.println(exp.getMessage());
                                                    isInputValid = false;
                                                    currentInput = read.nextLine();
                                                }
                                            }
                                            while (!isInputValid);

                                            // }


                                        }

                                    } catch (InputException exp) {
                                        System.out.println(exp.getMessage());
                                        isInputValid = false;
                                        currentInput = read.nextLine();
                                    }
                                } while (!isInputValid);


                            }


                            System.out.println("Are you sure you want to chose option " + "'" + currentInput.toUpperCase() +
                                    "'?");
                            System.out.println("Enter 'n' to CHANGE your answer OR 'y' to CONFIRM your " +
                                    "current choice!");

                            //using a separate variable since currenInput will be used for checkingAnswer (so cannot
                            // overwrite that here...
                            String playerConfirmation = read.nextLine();
                            do {
                                try {
                                    isInputValid = InputException.validateYesNoInput(playerConfirmation);
                                } catch (InputException ix) {
                                    isInputValid = false;
                                    System.out.println(ix.getMessage());
                                    playerConfirmation = read.nextLine();
                                }
                            }
                            while (!isInputValid);


                            if (playerConfirmation.equalsIgnoreCase("N")) {
                                System.out.println("Alright then, what's your new answer?");
                                currentInput = read.nextLine();
                                do {
                                    try {
                                        //since this is confirmation, user input has to be a b c or d and cannot be
                                        // lifeline (i.e. 1 2 3)
                                        isInputValid = InputException.validatePlayerChoiceInput(question, 0, currentInput);
                                    } catch (InputException ix) {
                                        isInputValid = false;
                                        System.out.println("Invalid input. Please enter valid characters only ('a', 'b', 'c'," +
                                                " 'd'):");
                                        currentInput = read.nextLine();
                                    }
                                }
                                while (!isInputValid);
                            }


                            //Checking answers
                            boolean isAnswerCorrect = checkAnswer(currentInput, question.getINDEX_OF_CORRECT_ANSWER());
                            if (isAnswerCorrect) {
                                playerWinnings = questionDollarValue;
                                System.out.println("Wooohoooo!!! That's the RIGHT answer! You've just won $" + playerWinnings);
                            } else {
                                System.out.println("Whooooops! That was the WRONG answer! The correct choice was " + ANSWER_CHOICE_INDICES.get(question.getINDEX_OF_CORRECT_ANSWER()) + ". " + question.getCorrectAnswer());
                                System.out.println("You just lost ALL your money!");

                                playerWinnings = 0;
                                playerAnswerIncorrect = true;
                                break;
                            }
                            System.out.println();
                        }
                    }


                    System.out.println("Thank you for playing " + player.getName().toUpperCase() + "!\n" +
                            "You get to take home $" + playerWinnings + ". Have " +
                            "a nice day!");
                    break;
                case 2: //Rules Page
                    goBackToMenu = true;
                    displayRulesPage();
                    boolean isInputValid;
                    String backInput = read.nextLine();
                    do {
                        try {
                            if (backInput.length() != 1)
                                throw new Exception("Input should be SINGLE character only: ");

                            if (!backInput.equalsIgnoreCase("b"))
                                throw new Exception("Invalid input. Enter 'b' to go back: ");

                            isInputValid = true;
                        } catch (Exception ex) {
                            isInputValid = false;
                            System.out.println(ex.getMessage());
                            backInput = read.nextLine();
                        }
                    }
                    while (!isInputValid);

                    break;
                case 3: //Exit without playing
                    goBackToMenu = false;
                    System.out.println("Sorry to see you go, until next time!");
                default:
                    goBackToMenu = false;
                    break;
            }
        } while (goBackToMenu);
    }

    public static void displayMenuPage() {
        System.out.println();
        System.out.println("1 - Start Game");
        System.out.println("2 - View Game Rules");
        System.out.println("3 - Exit Game");
        System.out.println();
    }


    public static void displayQuestion(Question question, Player player) {
        System.out.println("===========================================");
        System.out.println(question.getQuestion());
        for (int i = 0; i < question.getAllOptions().size(); i++) {
            System.out.println(ANSWER_CHOICE_INDICES.get(i) + ". " + question.getAllOptions().get(i));
        }
    }

    public static void displayLifelines(Player player) {
        ArrayList<Lifeline> playerLifelines = player.getLifelines();
        System.out.println("**********");
        System.out.println("Available Lifelines: ");
        if (playerLifelines.size() != 0) {
            String llString = "";
            for (int i = 1; i <= playerLifelines.size(); i++) {
                llString += (i + ". " + playerLifelines.get(i - 1).getName());
                if (i != playerLifelines.size()) { //append "|" except for the last element
                    llString += "  |  ";
                }
            }
            System.out.println(llString);
            System.out.println("**********");
            System.out.println("Enter your answer (A | B | C | D) or Lifeline (1 | 2 | 3):");
        } else {
            System.out.println("NONE");
            System.out.println("**********");
            System.out.println("Enter your answer (A | B | C | D): ");
        }
    }


    public static boolean isValidLifelineInput(String userInput) {
        return (userInput.equals("1") || userInput.equals("2") || userInput.equals("3"));
    }

    public static boolean checkAnswer(String playerChoice, int correctAnswerIndex) {
        int indexOfPlayerAnswer = ANSWER_CHOICE_INDICES.indexOf(playerChoice.toUpperCase());
        return indexOfPlayerAnswer == correctAnswerIndex;
    }

    public static void displayRulesPage() {
        System.out.println("-----------------------------------------------");
        System.out.println("WHO WANTS TO BE A MILLIONAIRE?");
        System.out.println("Welcome to the game playa! It's a pretty straightforward game. We give you a bunch of " +
                "questions, and you answer them to win some money, upto a MILLION DOLLARS!!\nThere are RULES " +
                "though!\n\n1. GAME MODES:\n\tEASY:\n\tYou get 9 questions in total divided equally in 3 ROUNDS, " +
                "each " +
                "with increasing difficulty.\n\tHARD:\n\tYou get 15 questions in total divided equally in 3 ROUNDS, " +
                "each with increasing difficulty as well.\n");
        System.out.println("2. LIFELINES:\n\tYou get 3 Lifelines at the beginning of your game,\n\t\ta. 50/50 - Two " +
                "WRONG" +
                " " +
                "choices will be automatically deleted for you increasing your chances of winning.\n\t\tb. Audience " +
                "Poll " +
                "- Audience will tell you what they think is the right answer.\n\t\tc. Phone a Friend - You can call " +
                "your" +
                " friend and ask them for an answer.\n\n");
        System.out.println("- You will be allowed to change your answer before confirming\n- After EVERY round, you" +
                " get an option to \"WALK " +
                "AWAY\" with the amount you've " +
                "won so" +
                " far" +
                " " +
                "(Except for the last round, DUH).\n- You can use each lifeline only ONCE, but can use multiple " +
                "lifeline " +
                "of an single question! Also in HARD mode, lifelines are not available to you until ROUND 2.\n- If " +
                "you don't walk " +
                "away and you answer any question incorrectly, you " +
                "automatically forfeit your won amount and go home EMPTY HANDED, Sorry bruh! That's the rules...");

        System.out.println("-----------------------------------------------");

        System.out.println("Enter 'b' to go BACK to Menu Page:");
    }

}
