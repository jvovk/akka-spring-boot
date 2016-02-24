package com.aggregator.actors;

import akka.actor.UntypedActor;
import com.aggregator.db.IWeatherRepository;
import com.aggregator.kafka.IProducer;
import com.aggregator.model.WeatherAverageNotification;
import com.aggregator.model.WeatherNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 22:46
 */
@Named("DbAggregatorActor")
@Scope("prototype")
public class DbAggregatorActor extends UntypedActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbAggregatorActor.class);

    @Autowired
    private IWeatherRepository weatherRepository;

    @Autowired
    private IProducer weatherAvgNotProducer;

    @Override
    public void preStart() {
        LOGGER.info("Starting db aggregator actor...");
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        WeatherNotification notification = (WeatherNotification) msg;
        LOGGER.debug("Aggregating by notification parameters: {}, {}", notification.getCity(), notification.getStreet());
        WeatherAverageNotification weatherAvgNotification =
                weatherRepository.getAverageInfoByCityAndStreet(notification.getCity(),
                notification.getStreet());
        LOGGER.debug("Saving to message broker: {}", weatherAvgNotification);
        weatherAvgNotProducer.produceWeatherAvgNotification(weatherAvgNotification);
    }
}
