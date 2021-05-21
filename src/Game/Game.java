package Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    private ArrayList<Question> mixedQuestionBank;
    private ArrayList<Question> easyQuestionBank, mediumQuestionBank, hardQuestionBank;
    private ArrayList<ArrayList<Question>> sortedQuestionBank;
    private Random random;
    private final int[] DOLLAR_VALUES_EASY_MODE = new int[]{100, 500, 1000, 8000, 16000, 32000, 125000, 500000, 1000000};
    private final int[] DOLLAR_VALUES_HARD_MODE = new int[]{100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000,
            125000, 250000, 500000, 1000000};
    private final boolean isGameModeEasy;


    //Used for Questions per Round calculation and its implementation logic in Launch class
    private final int MAX_QUESTIONS_EASY = 9;
    private final int MAX_QUESTIONS_HARD = 15;
    private final int NO_OF_ROUNDS = 3;


    public Game(boolean isGameModeEasy) {
        this.isGameModeEasy = isGameModeEasy;

        random = new Random();
        initializeQuestions();
        sortQuestionBank();
    }


    public ArrayList<Question> getGameQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        int questionsPerRound = isGameModeEasy ? (MAX_QUESTIONS_EASY/NO_OF_ROUNDS) : (MAX_QUESTIONS_HARD/NO_OF_ROUNDS);
        for (int i = 0; i < NO_OF_ROUNDS; i++) {
            //sortedQuestionBank = [[easyQuestionBank], [mediumQuestionBank], [hardQuestionBank]]
            for (int j = 0; j < questionsPerRound; j++) {
                int randomIndex = random.nextInt(sortedQuestionBank.get(i).size());
                questions.add(sortedQuestionBank.get(i).get(randomIndex));
                sortedQuestionBank.get(i).remove(randomIndex); //so that questions NEVER get repeated
            }
        }

        return questions;
    }

    public int getDollarValueForQuestion(int index) {
        return (isGameModeEasy ? DOLLAR_VALUES_EASY_MODE[index] : DOLLAR_VALUES_HARD_MODE[index]);
    }

    private void sortQuestionBank() {
        easyQuestionBank = new ArrayList<>();
        mediumQuestionBank = new ArrayList<>();
        hardQuestionBank = new ArrayList<>();

        for (Question question : mixedQuestionBank) {
            if (question.getDifficulty() == 'E') {
                easyQuestionBank.add(question);
            } else if (question.getDifficulty() == 'M') {
                mediumQuestionBank.add(question);
            } else if (question.getDifficulty() == 'H') {
                hardQuestionBank.add(question);
            }
        }

        sortedQuestionBank = new ArrayList<>();
        sortedQuestionBank.add(easyQuestionBank);
        sortedQuestionBank.add(mediumQuestionBank);
        sortedQuestionBank.add(hardQuestionBank);
    }


    //20 Question (Easy) + 20 Questions (Medium) + 10 Questions (Hard) = 50 Total
    private void initializeQuestions() {
        mixedQuestionBank = new ArrayList<>();

        mixedQuestionBank.add(new Question("Which sign of the zodiac is represented by the Crab?", "Cancer",
                new ArrayList<>(Arrays.asList("Cancer", "Libra", "Virgo", "Sagittarius")), 'E'));

        mixedQuestionBank.add(new Question("What is the shape of the toy invented by Hungarian professor Ernő Rubik?", "Cube"
                , new ArrayList<>(Arrays.asList("Cube", "Sphere", "Cylinder", "Pyramid")), 'E'));
        mixedQuestionBank.add(new Question("What is on display in the Madame Tussaud's museum in London?", "Wax Sculptures"
                , new ArrayList<>(Arrays.asList("Wax Sculptures", "Designer Clothing", "Unreleased Film Reels", "Vintage Cars")), 'E'));
        mixedQuestionBank.add(new Question("Which restaurant's mascot is a clown?", "McDonald's"
                , new ArrayList<>(Arrays.asList("McDonald's", "Wendy's", "Burger King", "A&W")), 'E'));
        mixedQuestionBank.add(new Question("The Flag of the European Union has how many stars on it?", "12"
                , new ArrayList<>(Arrays.asList("12", "10", "14", "16")), 'E'));
        mixedQuestionBank.add(new Question("What is the name of NASA's most famous space telescope?", "Hubble Space Telescope"
                , new ArrayList<>(Arrays.asList("Hubble Space Telescope", "Big Eye", "Death Star", "Millennium Falcon")), 'E'));
        mixedQuestionBank.add(new Question("Virgin Trains, Virgin Atlantic and Virgin Racing, are all companies owned by which famous entrepreneur?", "Richard Branson"
                , new ArrayList<>(Arrays.asList("Richard Branson", "Alan Sugar", "Donald Trump", "Bill Gates")), 'E'));
        mixedQuestionBank.add(new Question("What is the largest organ of the human body?", "Skin"
                , new ArrayList<>(Arrays.asList("Skin", "Heart", "Liver", "Large Intestine")), 'E'));
        mixedQuestionBank.add(new Question("How many colors are there in a rainbow?", "7"
                , new ArrayList<>(Arrays.asList("7", "8", "9", "10")), 'E'));
        mixedQuestionBank.add(new Question("Which of these colours is NOT featured in the logo for Google?", "Pink"
                , new ArrayList<>(Arrays.asList("Pink", "Yellow", "Blue", "Green")), 'E'));
        mixedQuestionBank.add(new Question("What do the letters in the GMT time zone stand for?", "Greenwich Mean Time"
                , new ArrayList<>(Arrays.asList("Greenwich Mean Time", "Global Median Time", "General Median Time", "Glasgow Man Time")), 'E'));
        mixedQuestionBank.add(new Question("According to Sherlock Holmes, \"If you eliminate the impossible, whatever " +
                "remains, however improbable, must be the...\"?", "Truth"
                , new ArrayList<>(Arrays.asList("Truth", "Answer", "Cause", "Source")), 'E'));
        mixedQuestionBank.add(new Question("What do the letters of the fast food chain KFC stand for?", "Kentucky Fried Chicken"
                , new ArrayList<>(Arrays.asList("Kentucky Fried Chicken", "Kentucky Fresh Cheese", "Kibbled Freaky Cow", "Kiwi Food Cut")), 'E'));
        mixedQuestionBank.add(new Question("When someone is sad they are said to be what color?", "Blue"
                , new ArrayList<>(Arrays.asList("Blue", "Green", "Red", "Yellow")), 'E'));
        mixedQuestionBank.add(new Question("What is Tasmania?", "An Australian State"
                , new ArrayList<>(Arrays.asList("An Australian State", "A flavor of Ben and Jerry's ice-cream", "A Psychological Disorder", "The Name of a Warner Brothers Cartoon Character")), 'E'));
        mixedQuestionBank.add(new Question("Earth is located in which galaxy?", "Milky Way"
                , new ArrayList<>(Arrays.asList("Milky Way", "Andromeda", "Galaxy Note", "Black Hole")), 'E'));
        mixedQuestionBank.add(new Question("The Canadian $1 coin is colloquially known as a what?", "Loonie"
                , new ArrayList<>(Arrays.asList("Loonie", "Boolie", "Foolie", "Moodie")), 'E'));
        mixedQuestionBank.add(new Question("Red Vines is a brand of what type of candy?", "Licorice"
                , new ArrayList<>(Arrays.asList("Licorice", "Lollipop", "Chocolate", "Bubblegum")), 'E'));
        mixedQuestionBank.add(new Question("What type of animal was Harambe?", "Gorilla"
                , new ArrayList<>(Arrays.asList("Gorilla", "Tiger", "Panda", "Crocodile")), 'E'));
        mixedQuestionBank.add(new Question("What is the name of the Jewish New Year?", "Rosh Hashanah"
                , new ArrayList<>(Arrays.asList("Rosh Hashanah", "Elul", "New Year", "Succoss")), 'E'));

        mixedQuestionBank.add(new Question("In a standard set of playing cards, which is the only King without a " +
                "moustache?", "Hearts"
                , new ArrayList<>(Arrays.asList("Hearts", "Spades", "Diamonds", "Clubs")), 'M'));
        mixedQuestionBank.add(new Question("A doctor with a PhD is a doctor of what?", "Philosophy"
                , new ArrayList<>(Arrays.asList("Philosophy", "Psychology", "Phrenology", "Physical Therapy")), 'M'));
        mixedQuestionBank.add(new Question("Which essential condiment is also known as Japanese horseradish?", "Wasabi"
                , new ArrayList<>(Arrays.asList("Wasabi", "Mentsuyu", "Karashi", "Ponzu")), 'M'));
        mixedQuestionBank.add(new Question("What was the destination of the missing flight MH370?", "Beijing"
                , new ArrayList<>(Arrays.asList("Beijing", "Kuala Lumpur", "Singapore", "Tokyo")), 'M'));
        mixedQuestionBank.add(new Question("What is the name given to Indian food cooked over charcoal in a clay oven?", "Tandoori"
                , new ArrayList<>(Arrays.asList("Tandoori", "Biryani", "Pani Puri", "Tiki Masala")), 'M'));
        mixedQuestionBank.add(new Question("What is the last letter of the Greek alphabet?", "Omega"
                , new ArrayList<>(Arrays.asList("Omega", "Mu", "Epsilon", "Kappa")), 'M'));
        mixedQuestionBank.add(new Question("What character was once considered to be the 27th letter of the alphabet?", "Ampersand"
                , new ArrayList<>(Arrays.asList("Ampersand", "Interrobang", "Tilde", "Pilcrow")), 'M'));
        mixedQuestionBank.add(new Question("What direction does the Statue of Liberty face?", "Southeast"
                , new ArrayList<>(Arrays.asList("Southeast", "Southwest", "Northwest", "Northeast")), 'M'));
        mixedQuestionBank.add(new Question("When did Facebook launch?", "2004"
                , new ArrayList<>(Arrays.asList("2004", "2005", "2003", "2006")), 'M'));
        mixedQuestionBank.add(new Question("What was the original name of the search engine Google?", "BackRub"
                , new ArrayList<>(Arrays.asList("BackRub", "CatMassage", "SearchPro", "Netscape Navigator")), 'M'));
        mixedQuestionBank.add(new Question("Where did the Pineapple plant originate?", "South America"
                , new ArrayList<>(Arrays.asList("South America", "Hawaii", "Europe", "Asia")), 'M'));
        mixedQuestionBank.add(new Question("Which item of clothing is usually worn by a Scotsman at a wedding?", "Kilt"
                , new ArrayList<>(Arrays.asList("Kilt", "Skirt", "Dress", "Rhobes")), 'M'));
        mixedQuestionBank.add(new Question("What was the name given to Japanese military dictators who ruled the country through the 12th and 19th Century?", "Shogun"
                , new ArrayList<>(Arrays.asList("Shogun", "Ninja", "Samurai", "Shinobi")), 'M'));
        mixedQuestionBank.add(new Question("A statue of Charles Darwin sits in what London museum?", "Natural History Museum"
                , new ArrayList<>(Arrays.asList("Natural History Museum", "Tate", "British Museum", "Science Museum")),
                'M'));
        mixedQuestionBank.add(new Question("What is the full title of the Prime Minister of the UK?", "First Lord of the Treasury"
                , new ArrayList<>(Arrays.asList("First Lord of the Treasury", "Duke of Cambridge", "Her Majesty's Loyal Opposition", "Manager of the Crown Estate")), 'M'));
        mixedQuestionBank.add(new Question("The Fields Medal, one of the most sought after awards in mathematics, is " +
                "awarded every how many years?", "4"
                , new ArrayList<>(Arrays.asList("4", "3", "5", "6")), 'M'));
        mixedQuestionBank.add(new Question("Which fast food chain has the most locations globally?", "Subway"
                , new ArrayList<>(Arrays.asList("Subway", "Starbucks", "McDonald's", "KFC")), 'M'));
        mixedQuestionBank.add(new Question("Which of these is the name of a Japanese system of alternative medicine, " +
                "literally meaning \"finger pressure\"?", "Shiatsu"
                , new ArrayList<>(Arrays.asList("Shiatsu", "Ukiyo", "Majome", "Ikigai")), 'M'));
        mixedQuestionBank.add(new Question("Which country drives on the left side of the road?", "Japan"
                , new ArrayList<>(Arrays.asList("Japan", "Germany", "Russia", "China")), 'M'));
        mixedQuestionBank.add(new Question("In the Morse code, which letter is indicated by 3 dots?", "S"
                , new ArrayList<>(Arrays.asList("S", "O", "A", "C")), 'M'));

        mixedQuestionBank.add(new Question("The words \"bungalow\" and \"shampoo\" originated from the languages of which " +
                "country?", "India"
                , new ArrayList<>(Arrays.asList("India", "Papua New Guinea", "Ethiopia", "China")), 'H'));
        mixedQuestionBank.add(new Question("Before the 19th Century, the \"Living Room\" was originally called the...?",
                "Parlor"
                , new ArrayList<>(Arrays.asList("Parlor", "Open Room", "Sitting Room", "Loft")), 'H'));
        mixedQuestionBank.add(new Question("Which of the following is not another name for the eggplant?", "Potimarron"
                , new ArrayList<>(Arrays.asList("Potimarron", "Brinjal", "Guinea Squash", "Melongene")), 'H'));
        mixedQuestionBank.add(new Question("Which one of these Swedish companies was founded in 1943?", "IKEA"
                , new ArrayList<>(Arrays.asList("IKEA", "H & M", "Lindex", "Clas Ohlson")), 'H'));
        mixedQuestionBank.add(new Question("What was Bank of America originally established as?", "Bank of Italy"
                , new ArrayList<>(Arrays.asList("Bank of Italy", "Bank of Long Island", "Bank of Pennsylvania", "Bank" +
                " of " +
                "Charlotte")), 'H'));
        mixedQuestionBank.add(new Question("Which product did Nokia, the telecommunications company, originally sell?",
                "Paper"
                , new ArrayList<>(Arrays.asList("Paper", "Phones", "Computers", "Processors")), 'H'));
        mixedQuestionBank.add(new Question("Who founded the Khan Academy?", "Sal Khan"
                , new ArrayList<>(Arrays.asList("Sal Khan", "Ben Khan", "Kitt Khan", "Adel Khan")), 'H'));
        mixedQuestionBank.add(new Question("Named after the mallow flower, mauve is a shade of what?", "Purple"
                , new ArrayList<>(Arrays.asList("Purple", "Red", "Brown", "Pink")), 'H'));
        mixedQuestionBank.add(new Question("How many notes are there on a standard grand piano?", "88"
                , new ArrayList<>(Arrays.asList("88", "98", "108", "78")), 'H'));
        mixedQuestionBank.add(new Question("What is the most commonly used noun in the English language?", "Time"
                , new ArrayList<>(Arrays.asList("Time", "Home", "Water", "Man")), 'H'));

    }

    //GETTERS
    public int getMAX_QUESTIONS_EASY() {
        return MAX_QUESTIONS_EASY;
    }

    public int getMAX_QUESTIONS_HARD() {
        return MAX_QUESTIONS_HARD;
    }

    public int getNO_OF_ROUNDS() {
        return NO_OF_ROUNDS;
    }
}
