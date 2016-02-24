package com.aggregator.converters;

import com.aggregator.model.WeatherAverageNotification;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:12
 */
public class WeatherAvgNotificationConverter implements Encoder<WeatherAverageNotification>, Decoder<WeatherAverageNotification> {

    private Gson gson;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherAvgNotificationConverter.class);

    public WeatherAvgNotificationConverter(VerifiableProperties verifiableProperties) {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public byte[] toBytes(WeatherAverageNotification weatherAvgNotification) {
        return toJsonString(weatherAvgNotification).getBytes();
    }

    public String toJsonString(WeatherAverageNotification weatherAvgNotification) {
        return gson.toJson(weatherAvgNotification);
    }

    @Override
    public WeatherAverageNotification fromBytes(byte[] bytes) {
        try {
            return gson.fromJson(new String(bytes, "UTF-8"), WeatherAverageNotification.class);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Can't convert json to object ", e.getMessage());
        }
        return null;
    }

    public WeatherAverageNotification fromString(String string) {
        return gson.fromJson(string, WeatherAverageNotification.class);
    }
}
