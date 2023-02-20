package org.example.Topic;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CurrentProduserData {

    private final double priceDown = 10;
    private double currentPrice;
    private double minPrice;

    @Setter
    private boolean auction = true;

    public void updateData(double minPrice) {
        this.minPrice = minPrice;
        this.currentPrice = 2 * this.minPrice;
        this.auction = true;
    }

    public void changePrice(double otherPrice) {
        if (otherPrice - priceDown > minPrice) {
            currentPrice = otherPrice - priceDown;
        } else {
            currentPrice = minPrice;
            auction = false;
        }
        log.info("current Price: {}", currentPrice);
//        } else {
//            currentPrice = minPrice;
//            auction = false;
//        }
    }
}
