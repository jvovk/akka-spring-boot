package com.aggregator.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.aggregator.db.IWeatherRepository;
import com.aggregator.model.WeatherNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 22:45
 */
@Named("DbSaverActor")
@Scope("prototype")
public class DbSaverActor extends UntypedActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbSaverActor.class);

    @Autowired
    private IWeatherRepository weatherRepository;

    @Autowired
    @Qualifier("dbAggregatorActor")
    private ActorRef childDbAggregatorActor;

    @Override
    public void preStart() {
        LOGGER.info("Starting db saver actor...");
    }

    @Override
    public void onReceive(Object msg) {
        LOGGER.debug("Saving to db notification: {}", msg);
        weatherRepository.insert((WeatherNotification) msg);
        childDbAggregatorActor.tell(msg, getSelf());
    }

}
