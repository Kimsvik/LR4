package org.example.Topic;

import jade.core.AID;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Data {
    private AID topic;
    private Map<AID, Double> bitsData = new HashMap<>();

}
