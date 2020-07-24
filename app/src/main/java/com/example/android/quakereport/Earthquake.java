package com.example.android.quakereport;

public class Earthquake
{
    private double magnitude;
    private long time;
    private String location;
    private String url;
    public Earthquake(double magnitude,String location,long time,String url)
    {
        this.magnitude=magnitude;
        this.time=time;
        this.location=location;
        this.url=url;
    }
    public double getMagnitude()
    {
        return magnitude;
    }
    public String getLocation()
    {
        return location;
    }
    public long getTime()
    {
        return time;
    }
    public String getUrl()
    {
        return url;
    }

}
