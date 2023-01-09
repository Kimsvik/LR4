package org.example.BDistributor;

import org.example.Support.JsonParser;
import org.example.Topic.ConsumerData;
import org.example.Topic.DistributorData;
import org.example.Topic.TopicData;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;


public class SendFirstData extends WakerBehaviour {
    private TopicData topicData;
    private ConsumerData consumerData;


    public SendFirstData(Agent a, long timeout, TopicData topicData, ConsumerData consumerData) {
        super(a, timeout);
        this.topicData = topicData;
        this.consumerData = consumerData;
    }

    @Override
    protected void onWake() {
        ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
        msg.setContent(JsonParser.dataToString(new DistributorData(
                consumerData.getTime(),
                consumerData.getPower())));
        msg.setProtocol("time, power");
        msg.addReceiver(topicData.getTopic());
        getAgent().send(msg);
    }
}
