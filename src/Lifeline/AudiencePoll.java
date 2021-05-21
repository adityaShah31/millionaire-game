package Lifeline;

import Game.Question;

import java.util.ArrayList;

public class AudiencePoll extends Lifeline {

    private int numberOfValidAnswers = 4;
    private String AUDIENCE_SUGGESTION_SUFFIX = "  ==> ";

    public AudiencePoll() {
        super("Audience Poll");
    }

    @Override
    public void useLifeline(Question question) {

        //output will ALWAYS be either 0 or 1, i.e. binary
        boolean isAudienceRight = random.nextInt(2) == 0;

        //check how many "valid" options does the question have. Will be 2 if 50/50 already used on that question. or
        // else will be 4
        for (String option : question.getAllOptions()) {
            if (option.equals(FIFTY_FIFTY_HASH_STRING)) {
                numberOfValidAnswers = 2;
                break;
            }
        }

        ArrayList<Integer> probabilities = generateRandomProbabilities(100, numberOfValidAnswers, isAudienceRight);
        ArrayList<String> allOptions = question.getAllOptions();

        if (isAudienceRight) {
            //mapping the first element (highest %) of probabilities to the correct answer in question object
            String tempSavedAnswer =
                    allOptions.get(question.getINDEX_OF_CORRECT_ANSWER()) + AUDIENCE_SUGGESTION_SUFFIX + probabilities.get(0) + "%";
            allOptions.set(question.getINDEX_OF_CORRECT_ANSWER(), tempSavedAnswer);

            allOptions.remove(question.getINDEX_OF_CORRECT_ANSWER());
            probabilities.remove(0); //removing the already mapped probability so that others can't use it

            if (numberOfValidAnswers == 2) { //50/50 already applied to the question
                //probabilities will only have 1 value since the other one is already applied to the correct answer.
                //allOptions will have 3 values, 2 crossed out and 1 incorrect
                for (int i = 0; i < allOptions.size(); i++) {
                    if (!allOptions.get(i).equals(FIFTY_FIFTY_HASH_STRING)) {
                        String updatedOption = allOptions.get(i) + AUDIENCE_SUGGESTION_SUFFIX + probabilities.get(0);
                        allOptions.set(i, updatedOption);
                        break;
                    }
                }
            }
            else { //probabilities will have 3 values, and allOptions will have 3 values
                //hence one to one index mapping can be done directly using a loop.
                for (int i = 0; i < probabilities.size(); i++) {
                    String answerString =
                            allOptions.get(i) + AUDIENCE_SUGGESTION_SUFFIX + probabilities.get(i) + "%";
                    allOptions.set(i, answerString);
                }
            }


            //adding back the tempSavedAnswer back at its correct index so that the question object is unchanged
            allOptions.add(question.getINDEX_OF_CORRECT_ANSWER(), tempSavedAnswer);

        } else {
            for (int i = 0; i < allOptions.size(); i++) {
                String answerString = allOptions.get(i);
                if (!answerString.equals(FIFTY_FIFTY_HASH_STRING)) {
                    //since probabilities only has 2 values and allOptions has 4, it's an easy recipe for
                    // indexOutOfBoundsError...
                    //Consider case where allOptions = ["x-x-x-x", "x-x-x-x", "Correct Answer", "Incorrect Answer"]
                    //probabilities will always be [68, 32] or some other 2 number combo. probability(i) would throw
                    // error for i >= 2 since it only has 2 values
                    allOptions.set(i, answerString + AUDIENCE_SUGGESTION_SUFFIX + probabilities.get(0) + "%");
                    probabilities.remove(0);
                }
            }
        }
    }


    private ArrayList<Integer> generateRandomProbabilities(int maxValue, int optionsCount, boolean isAudienceRight) {
        ArrayList<Integer> probabilities = new ArrayList<>();
        int initialBias = isAudienceRight ? (maxValue / 2 + 1) : 0;
        for (int i = 0; i < optionsCount - 1; i++) {
            int probability = getRandomNumberFromRange(initialBias, maxValue);
            initialBias = 0;
            maxValue -= probability;
            probabilities.add(probability);
        }
        probabilities.add(maxValue);

        return probabilities;
    }

}
