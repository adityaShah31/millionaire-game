package Lifeline;

import Game.Question;

import java.util.Random;

public abstract class Lifeline {
    private String name;
    protected Random random;
    protected final String FIFTY_FIFTY_HASH_STRING = "x-x-x-x";

    public Lifeline(String name) {
        this.name = name;
        random = new Random();
    }

    public abstract void useLifeline(Question question);

    //will spit out random value from range including both minValue & maxValue
    protected int getRandomNumberFromRange(int minValue, int maxValue) {
        return random.nextInt((maxValue - minValue) + 1) + minValue;
    }

    //GETTERS
    public String getName() {
        return name;
    }
}
