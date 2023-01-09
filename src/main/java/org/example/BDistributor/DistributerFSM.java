package org.example.BDistributor;

import org.example.Topic.ConsumerData;
import org.example.Topic.Data;
import org.example.Topic.WinnerProducerData;
import jade.core.behaviours.FSMBehaviour;

public class DistributerFSM extends FSMBehaviour {
    private static final String SEND_TOPIC="send_topic",
            SEND_DATA="send_data",
            COLLECT="collect",
            ACCEPT="accept",
            CONFIRM_PRICE = "confirm_price";

    public DistributerFSM(ConsumerData consumerData) {
        Data topicData = new Data();
        WinnerProducerData winner = new WinnerProducerData();

        registerFirstState(new SendTopicName(topicData), SEND_TOPIC);
        registerState(new SendFirstData(myAgent, 500, topicData, consumerData), SEND_DATA);
        registerState(new CollectBitsParallel(topicData), COLLECT);
        registerLastState(new ConfirmPrice(topicData, consumerData, winner), CONFIRM_PRICE);
//        registerLastState(new SendAgreePrice(topicData, winner), ACCEPT);

        registerDefaultTransition(SEND_TOPIC, SEND_DATA);
        registerDefaultTransition(SEND_DATA, COLLECT);
        registerDefaultTransition(COLLECT, CONFIRM_PRICE);
//        registerDefaultTransition(CONFIRM_PRICE, ACCEPT);
    }
}
