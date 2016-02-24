package com.aggregator.kafka;

import com.aggregator.model.WeatherAverageNotification;

/**
 * Author: Y. Vovk
 * 08.02.16.
 */
public class WeatherAvgNotificationConsumer implements IConsumer<WeatherAverageNotification> {

    @Override
    public void submitMessage(final WeatherAverageNotification message) {
        //
    }
}
