package com.example.M15_FinalProj.Weather;

import java.util.ArrayList;

public class WeatherResponse {
    public WeatherCoordinate coordinate;
    public WeatherInfo[] weather;
    public String base;
    public WeatherMain main;
    public int visibility;
    public WeatherWind wind;
    public WeatherCloud cloud;
    public int dt;
    public WeatherSystem sys;
    public int timezone;
    public String name;
    public int cod;
}
