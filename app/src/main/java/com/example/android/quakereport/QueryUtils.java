package com.example.android.quakereport;

/**
 * Created by saul on 14/1/2017.
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.util.StringBuilderPrinter;

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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.android.quakereport.model.Earthquake;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */

public class QueryUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     *
     * @param requestUrl
     * @return Earthquake List with all the data availabe or empty arrayList
     */
    public static List<Earthquake> fetchEartquakeData(String requestUrl){
        URL url = createUrl(requestUrl);

        String response = null;
        try{
            response = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem making the HTTP request.",e);
        }

        List<Earthquake> eartquakes = extractEarthquakes(response);

        return eartquakes;
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static ArrayList<Earthquake> extractEarthquakes(String response) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(response);
            JSONArray features =root.getJSONArray("features");
            for(int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                Double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                Date time = new Date(properties.getLong("time"));
                String url = properties.getString("url");
                Earthquake earthquake = new Earthquake(mag,place,time,url);
                earthquakes.add(earthquake);
                earthquake = null;
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String response = "";
        if (url != null){

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try{
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200){
                    inputStream = urlConnection.getInputStream();
                    response = readFromStream(inputStream);
                }

            }catch(IOException e){
                Log.e(LOG_TAG,"Error response code:" + urlConnection.getResponseCode());
            }finally{
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            }
        }
        return response;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
