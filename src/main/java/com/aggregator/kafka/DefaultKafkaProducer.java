package com.aggregator.kafka;

import com.aggregator.model.WeatherAverageNotification;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:12
 */
@Component
public class DefaultKafkaProducer implements IProducer {

    @Autowired
    @Qualifier("weatherAvgNotProducer")
    private Producer<String, WeatherAverageNotification> notificationProducer;

    public void produceWeatherAvgNotification(WeatherAverageNotification message) {
        notificationProducer.send(new KeyedMessage<>("weather-avg-notification", message.getCity(), message));
    }
}
