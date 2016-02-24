package com.aggregator.kafka;

import com.aggregator.model.WeatherAverageNotification;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:12
 */
public interface IProducer {

    void produceWeatherAvgNotification(WeatherAverageNotification message);
}
