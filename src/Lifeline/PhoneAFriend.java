package Lifeline;

import Game.Question;

import java.util.ArrayList;

public class PhoneAFriend extends Lifeline {

    private final String FRIEND_SUGGESTION_SUFFIX = "  <== Friend's suggestion!";

    public PhoneAFriend() {
        super("Phone a Friend");
    }

    @Override
    public void useLifeline(Question question) {

        //output will ALWAYS be either 0 or 1, i.e. binary
        boolean isFriendRight = random.nextInt(2) == 0;
        ArrayList<String> allOptions = question.getAllOptions();
        String friendSuggestion = "";

        if (isFriendRight) {
            friendSuggestion =  allOptions.get(question.getINDEX_OF_CORRECT_ANSWER()) + FRIEND_SUGGESTION_SUFFIX;
            allOptions.set(question.getINDEX_OF_CORRECT_ANSWER(), friendSuggestion);
        }
        else {

            //logic for when the question has 50/50 logic already applied to it, so randomIndex has to be random from
            // the 2 valid answers and not 4
            //store indexes of valid answers in arraylist, then choose random index value from this arraylist and apply
            // friend
            // suggestion
            ArrayList<Integer> validIndices = new ArrayList<>();

            for (int i = 0; i < allOptions.size(); i++) {
                if (!allOptions.get(i).equals(FIFTY_FIFTY_HASH_STRING))
                    validIndices.add(i);
            }

            int randomIndex =  validIndices.get(random.nextInt(validIndices.size())); //gets random value of a valid
            // index
            friendSuggestion = allOptions.get(randomIndex) + FRIEND_SUGGESTION_SUFFIX;
            allOptions.set(randomIndex, friendSuggestion);
        }

        showWaitingAnimation();
        System.out.println("Okay so here's what your friend thinks:");
    }


    private void showWaitingAnimation() {
        String progressSymbol = ". ";
        String progressString = "";

        int randomTimeInteger = getRandomNumberFromRange(10, 20); //minimum wait time 10 * 600ms = 6s, maximum wait
        // time 20 * 600ms = 12s
        for (int i = 0; i < randomTimeInteger; i++) {
            if (i % 5 == 0) { //will enter this block every 5th iteration, i.e. after every 4 "." (. . . .)
                progressString = "";
            } else {
                progressString += progressSymbol;
            }
            System.out.print("Friend is thinking ".toUpperCase() + progressString + "\r");


            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
