package com.aggregator.db;

import com.aggregator.model.WeatherAverageNotification;
import com.aggregator.model.WeatherNotification;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:32
 */
public interface IWeatherRepository {

    void insert(WeatherNotification notification);
    List<WeatherNotification> findAll(Query query);
    WeatherAverageNotification getAverageInfoByCityAndStreet(String city, String street);

}
