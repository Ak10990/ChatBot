package com.hiq.freedomvision.models;

public class WeatherResult {
    private Data data;

    private String type;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ClassPojo [data = " + data + ", type = " + type + "]";
    }

    public class Data {
        private String temperature;

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        @Override
        public String toString() {
            return "ClassPojo [temperature = " + temperature + "]";
        }
    }

}