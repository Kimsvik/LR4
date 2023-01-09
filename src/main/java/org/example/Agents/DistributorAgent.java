package org.example.Agents;

import org.example.DfHelper;
import org.example.AutorunnableAgent;
import org.example.BDistributor.AcceptRequest;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

@AutorunnableAgent(name = "D", startIndex = 1, count = 3)
@Slf4j
public class DistributorAgent extends Agent {

    @Override
    protected void setup() {
        DfHelper.registerAgent(this, "distributor:L" + getLocalName().substring(1));
        log.info("agent {} is ready", getLocalName());
        addBehaviour(new AcceptRequest());
    }
}
