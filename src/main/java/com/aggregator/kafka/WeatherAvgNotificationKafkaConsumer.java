package com.aggregator.kafka;

import com.aggregator.model.WeatherAverageNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:32
 */
public class WeatherAvgNotificationKafkaConsumer extends AbstractKafkaConsumer<WeatherAverageNotification> {

    @Autowired
    @Qualifier("weatherAvgNotConsumer")
    private IConsumer<WeatherAverageNotification> consumer;

    @Override
    public void submitMessage(final WeatherAverageNotification message) {
        consumer.submitMessage(message);
    }
}
