package com.example.android.quakereport;

import android.text.TextUtils;
import  android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

public class QueryUtils
{

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakesfromJSON(String JSON) {

        if(TextUtils.isEmpty(JSON))
        {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonroot=new JSONObject(JSON);
            JSONArray jsonArray=jsonroot.getJSONArray("features");
            for(int x=0;x<jsonArray.length();x++) {
                JSONObject jsonObject = jsonArray.getJSONObject(x);
                JSONObject jsonObject1 = jsonObject.getJSONObject("properties");
                double mag = Double.parseDouble(jsonObject1.getString("mag"));
                String place = jsonObject1.getString("place");
                long time = Long.parseLong(jsonObject1.getString("time"));
                String url=jsonObject1.getString("url");
                Earthquake earthquake = new Earthquake(mag, place, time,url);
                earthquakes.add(earthquake);
            }

        }
        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            e.printStackTrace();
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static URL createurl(String stringurl)
    {
        URL url=null;
        try
        {
            url=new URL (stringurl);
        }
        catch(MalformedURLException e)
        {
            Log.e(LOG_TAG,"Problem in URL",e);
        }
        return url;
    }
    private static String makeHTTPrequest(URL url)throws IOException
    {
        String JSONresponse="";
        if(url==null)
        {
            return JSONresponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try
        {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                JSONresponse=readfromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG,"Error response Code: "+urlConnection.getResponseCode());
            }
        }
        catch(IOException e)
        {
            Log.e(LOG_TAG,"Error retrieving the JSON Response",e);
        }
        finally
        {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }
        return JSONresponse;
    }
    private static String readfromStream(InputStream inputStream)throws IOException
    {
        StringBuilder stringBuilder=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line= bufferedReader.readLine();
            while(line!=null)
            {
                stringBuilder.append(line);
                line=bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }
    public static List<Earthquake> fetchearthquakedata(String url)
    {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        URL url1=createurl(url);
        String JSONresponse=null;
        try
        {
            JSONresponse=makeHTTPrequest(url1);
        }
        catch(IOException e)
        {
            Log.e(LOG_TAG,"Problem Making the HTTP request",e);
        }
        ArrayList<Earthquake> earthquake=extractEarthquakesfromJSON(JSONresponse);
        return earthquake;
    }


}
