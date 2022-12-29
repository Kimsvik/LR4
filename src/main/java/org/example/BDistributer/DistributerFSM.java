package org.example.BDistributer;

import org.example.External.ConsumerData;
import org.example.External.TopicData;
import org.example.External.WinnerProducerData;
import jade.core.behaviours.FSMBehaviour;

public class DistributerFSM extends FSMBehaviour {
    private static final String SEND_TOPIC="send_topic",
            SEND_DATA="send_data",
            COLLECT="collect",
            CONFIRM_PRICE = "confirm_price";

    public DistributerFSM(ConsumerData consumerData) {
        TopicData topicData = new TopicData();
        WinnerProducerData winner = new WinnerProducerData();

        registerFirstState(new SendTopicName(topicData), SEND_TOPIC);
        registerState(new SendFirstData(myAgent, 500, topicData, consumerData), SEND_DATA);
        registerState(new CollectBitsParallel(topicData), COLLECT);
        registerLastState(new ConfirmPrice(topicData, consumerData, winner), CONFIRM_PRICE);

        registerDefaultTransition(SEND_TOPIC, SEND_DATA);
        registerDefaultTransition(SEND_DATA, COLLECT);
        registerDefaultTransition(COLLECT, CONFIRM_PRICE);
    }
}
