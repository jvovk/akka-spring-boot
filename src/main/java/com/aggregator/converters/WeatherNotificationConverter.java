package com.aggregator.converters;

import com.aggregator.model.WeatherNotification;
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
public class WeatherNotificationConverter implements Encoder<WeatherNotification>, Decoder<WeatherNotification> {

    private Gson gson;

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherNotificationConverter.class);

    public WeatherNotificationConverter(VerifiableProperties verifiableProperties) {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public byte[] toBytes(WeatherNotification weatherNotification) {
        return toJsonString(weatherNotification).getBytes();
    }

    public String toJsonString(WeatherNotification weatherNotification) {
        return gson.toJson(weatherNotification);
    }

    @Override
    public WeatherNotification fromBytes(byte[] bytes) {
        try {
            return gson.fromJson(new String(bytes, "UTF-8"), WeatherNotification.class);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Can't convert json to object ", e.getMessage());
        }
        return null;
    }

    public WeatherNotification fromString(String string) {
        return gson.fromJson(string, WeatherNotification.class);
    }
}
