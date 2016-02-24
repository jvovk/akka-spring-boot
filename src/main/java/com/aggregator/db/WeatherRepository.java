package com.aggregator.db;

import com.aggregator.model.WeatherAverageNotification;
import com.aggregator.model.WeatherNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:50
 */
@Repository
public class WeatherRepository implements IWeatherRepository {

    private static final String COLLECTION_NAME = "notifications";

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void insert(WeatherNotification notification) {
        mongoOperations.insert(notification, COLLECTION_NAME);
    }

    @Override
    public List<WeatherNotification> findAll(Query query) {
        return mongoOperations.find(query, WeatherNotification.class, COLLECTION_NAME);
    }

    @Override
    public WeatherAverageNotification getAverageInfoByCityAndStreet(String city, String street) {
        List<WeatherNotification> notifications = findAll(query(where("city").is(city).and("street").is(street)));

        float avgTemperature  = notifications
                .parallelStream()
                .map(WeatherNotification::getTemp)
                .reduce(Float::sum)
                .get()/notifications.size();

        float avgLight  = notifications
                .parallelStream()
                .map(WeatherNotification::getLight)
                .reduce(Float::sum)
                .get()/notifications.size();

        return new WeatherAverageNotification(city, street, avgTemperature, avgLight);
    }
}
