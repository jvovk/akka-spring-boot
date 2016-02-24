package com.aggregator.kafka;

import akka.actor.ActorRef;
import com.aggregator.model.WeatherNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 17:18
 */
public class WeatherNotificationConsumer implements IConsumer<WeatherNotification> {

    @Autowired
    @Qualifier("initBaseActor")
    private ActorRef mainActor;

    @Override
    public void submitMessage(final WeatherNotification message) {
        mainActor.tell(message, null);
    }
}
