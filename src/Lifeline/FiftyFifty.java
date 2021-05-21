package Lifeline;

import Game.Question;

import java.util.ArrayList;

public class FiftyFifty extends Lifeline {

    public FiftyFifty() {
        super("50/50");
    }

    @Override
    public void useLifeline(Question question) {
        ArrayList<Integer> randomizedIncorrectIndexes = new ArrayList<>(); //will hold the indexes of 2 out of 3 incorrect
        // answers, selected randomly.

        //to ensure that no two values are repeated.
        while (randomizedIncorrectIndexes.size() != (question.getAllOptions().size() / 2)) {
            int randomIndex = random.nextInt(question.getAllOptions().size()); //spits out random number between 0 and 3
            if (!randomizedIncorrectIndexes.contains(randomIndex) && randomIndex != question.getINDEX_OF_CORRECT_ANSWER()) {
                randomizedIncorrectIndexes.add(randomIndex);
                question.getAllOptions().set(randomIndex, FIFTY_FIFTY_HASH_STRING); //invalidate/cross-out the
                // incorrect answer
            }
        }
    }

}
