package com.aggregator.kafka;

import com.aggregator.model.WeatherNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 17:18
 */
public class WeatherNotificationKafkaConsumer extends AbstractKafkaConsumer<WeatherNotification>{

    @Autowired
    @Qualifier("weatherNotConsumer")
    private IConsumer<WeatherNotification> consumer;

    @Override
    public void submitMessage(final WeatherNotification message) {
        consumer.submitMessage(message);
    }
}
