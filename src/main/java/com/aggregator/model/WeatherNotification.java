package com.aggregator.model;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:23
 */
public class WeatherNotification {

    private long timestamp;
    private String city;
    private String street;
    private float temp;
    private float light;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeatherNotification{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", city='").append(city).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", temp=").append(temp);
        sb.append(", light=").append(light);
        sb.append('}');
        return sb.toString();
    }
}
