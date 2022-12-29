package org.example.BProducer;

import org.example.External.CurrentProduserData;
import jade.core.AID;
import jade.core.behaviours.ParallelBehaviour;

public class Auction extends ParallelBehaviour {

    private AID topic;

    private CurrentProduserData currentProduserData;

    public Auction(AID topic, CurrentProduserData currentProduserData) {
        super(ParallelBehaviour.WHEN_ANY);
        this.topic = topic;
        this.currentProduserData = currentProduserData;
    }

    public void onStart() {
        addSubBehaviour(new ReceiveAnswers(topic, currentProduserData));
    }
}
