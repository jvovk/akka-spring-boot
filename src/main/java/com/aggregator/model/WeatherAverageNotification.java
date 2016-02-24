package com.aggregator.model;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:25
 */
public class WeatherAverageNotification {

    private String city;
    private String street;
    private float averageTemp;
    private float averageLight;

    public WeatherAverageNotification() {
    }

    public WeatherAverageNotification(String city, String street, float averageTemp, float averageLight) {
        this.city = city;
        this.street = street;
        this.averageTemp = averageTemp;
        this.averageLight = averageLight;
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

    public float getAverageTemp() {
        return averageTemp;
    }

    public void setAverageTemp(float averageTemp) {
        this.averageTemp = averageTemp;
    }

    public float getAverageLight() {
        return averageLight;
    }

    public void setAverageLight(float averageLight) {
        this.averageLight = averageLight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WeatherAverageNotification{");
        sb.append("city='").append(city).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", averageTemp=").append(averageTemp);
        sb.append(", averageLight=").append(averageLight);
        sb.append('}');
        return sb.toString();
    }
}
