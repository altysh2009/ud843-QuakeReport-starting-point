/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.Locale;

/**
 * Utility class with methods to help perform the HTTP request and
 * parse the response.
 */
public final class Utilts {

    /** Tag for the log messages */
    public static final String LOG_TAG = Utilts.class.getSimpleName();

    /**
     * Query the USGS dataset and return an {@link } object to represent a single earthquake.
     */
    public static ArrayList<custom_Earth> fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
      //  ArrayList<custom_Earth> earth = jasonextract(jsonResponse);

        // Return the {@link Event}
        return jasonextract(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("tag", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("tag", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public static ArrayList<custom_Earth> jasonextract(String jason){
        if(TextUtils.isEmpty(jason)){
            Log.d("Tag", "jasonextract: haahhahahahaahahahah ");
            return null;
        }
        ArrayList<custom_Earth> eaaeth = new ArrayList<custom_Earth>();
        try {
            JSONObject jsonRootObject = new JSONObject(jason);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("features");

            //Iterate the jsonArray and print the info of JSONObjects
            int d =0;

            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObject2 = jsonObject.getJSONObject("properties");
                String stre = jsonObject2.optString("mag");
                //Log.d("onCreate",stre);
                String city = jsonObject2.optString("place");


                String time = jsonObject2.optString("time");
                String url = jsonObject2.optString("url");
                Date dateObject = new Date(Long.parseLong(time));
                SimpleDateFormat dateFormatter = new SimpleDateFormat("DD MM yyyy  hh,mm,ss aa", Locale.ENGLISH);
                String dateToDisplay = dateFormatter.format(dateObject);



                eaaeth.add( new custom_Earth(city,dateToDisplay,stre,url));



            }
            return eaaeth;


        } catch (JSONException e) {
            Log.e("tag", "jasonextract: find ",e );
        }




        return null;

    }}
