package org.example.BProducer;

import org.example.Topic.ProducerData;
import org.example.Support.TopicHelper;
import org.example.Support.TimeClass;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ReceiveTopicName extends Behaviour {
    private ProducerData producerData;
    private final MessageTemplate mt = MessageTemplate.and(
            MessageTemplate.MatchPerformative(ACLMessage.INFORM),
            MessageTemplate.MatchProtocol("topicName"));

    public ReceiveTopicName(ProducerData data) {
        this.producerData = data;
    }

    @Override
    public void onStart() {
        myAgent.addBehaviour(new TickerBehaviour(myAgent, TimeClass.getTime()) {
            @Override
            protected void onTick() {
                log.info("\u001B[32m" + "unsold power {}" + "\u001B[0m", producerData.getPower());
            }
        });
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            log.debug("get topic name <{}>", msg.getContent());
            AID topic = TopicHelper.createTopic(myAgent, msg.getContent());
            myAgent.addBehaviour(new ProducerFSM(topic, producerData));
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}