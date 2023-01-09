package org.example.Agents;

import org.example.DfHelper;
import org.example.AutorunnableAgent;
import org.example.External.ProducerData;
import org.example.BProducer.ReceiveTopicName;
import org.example.CFG.ProducorCFG;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@AutorunnableAgent(name = "P", startIndex = 1, count = 3)
@Slf4j
public class ProducerAgent extends Agent {

    @Override
    protected void setup() {
        DfHelper.registerAgent(this, "producer");
        log.info("agent {} is ready", this.getLocalName());

        ProducorCFG cfg;
        try {
            JAXBContext context = JAXBContext.newInstance(ProducorCFG.class);
            Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
            cfg = (ProducorCFG) jaxbUnmarshaller.unmarshal(
                    new File("src/main/resources/LR4/Producer/"  + getLocalName() + ".xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        addBehaviour(new ReceiveTopicName(new ProducerData(cfg)));

    }
}


