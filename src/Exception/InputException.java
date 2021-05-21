package Exception;

import Game.Question;

import java.util.ArrayList;

public class InputException extends Exception {

    private static final String[] CHOICE_INDICES = new String[]{"a", "b", "c", "d"};

    public InputException(String message) {
        super(message);
    }

    public static boolean validateYesNoInput(String userInput) throws InputException {
        if (userInput.length() != 1)
            throw new InputException("Invalid! Input should be SINGLE character only...");

        if (userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("n")) {
            return true;
        } else {
            throw new InputException("Invalid input. Please enter either 'y' or 'n': ");
        }
    }


    public static boolean validatePlayerChoiceInput(Question question, int lifelineCount, String userInput) throws InputException {

        if (userInput.length() != 1)
            throw new InputException("Invalid! Input should be SINGLE character only...");

        ArrayList<String> validInputs = new ArrayList<>();

        if (lifelineCount != 0) {
            for (int i = 1; i <= lifelineCount; i++) {
                validInputs.add(String.valueOf(i));
            }
        }

        for (int i = 0; i < question.getAllOptions().size(); i++) {
            if (!question.getAllOptions().get(i).equals("x-x-x-x")) //if 50/50 is already applied to it, then don't
                // add the crossed-out answer's letter to valid choices
                validInputs.add(CHOICE_INDICES[i]);
        }


        if (validInputs.contains(userInput.toLowerCase())) {
            return true;
        } else {
            throw new InputException("Invalid input. Please select something from the AVAILABLE choices in front of " +
                    "you: ");
        }

    }


}
