package Game;

import Lifeline.*;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Lifeline> lifelines;

    public Player(String name) {
        this.name = name;

        allotLifelines();
    }

    public void useLifeline(Lifeline lifeline, Question forQuestion) {
        lifeline.useLifeline(forQuestion);
        lifelines.remove(lifeline); //so that a lifeline can be used only once!
    }




    private void allotLifelines() {
        FiftyFifty fiftyFifty = new FiftyFifty();
        PhoneAFriend phoneAFriend = new PhoneAFriend();
        AudiencePoll audiencePoll = new AudiencePoll();

        lifelines = new ArrayList<>();
        lifelines.add(fiftyFifty);
        lifelines.add(phoneAFriend);
        lifelines.add(audiencePoll);
    }


    //GETTERS
    public ArrayList<Lifeline> getLifelines() {
        return lifelines;
    }

    public String getName() {
        return name;
    }

}
