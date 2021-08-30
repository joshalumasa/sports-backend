package com.api.backend;

import com.api.backend.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sqlite.core.DB;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class ApiCalls {
    Constants property;

    public void fetchLeagues() throws Exception {

        // define api
        String url = Constants.leagues_endpoint /*+ Constants.nt_apiKey*/;

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

//        System.out.println("token--- "+Login.getAUTH());

        //add request header
        httpClient.setRequestMethod("GET");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        httpClient.setRequestProperty("x-rapidapi-key", "407f57c6c3msh39a5081824ca9eep1f6f52jsn6a9a6e410883");
        httpClient.setRequestProperty("x-rapidapi-host", "api-football-v1.p.rapidapi.com");
//        httpClient.setRequestProperty("AUTH", Login.getAUTH());

        int responseCode = httpClient.getResponseCode();

        System.out.println("responsecode: "+responseCode);

        InputStream result;

        if (responseCode < HttpsURLConnection.HTTP_BAD_REQUEST){
            result = httpClient.getInputStream();
        }
        else {
            result = httpClient.getErrorStream();
        }

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(result))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            JSONObject apiResponse = new JSONObject(response.toString());

//                System.out.println("res-- "+apiResponse);

            if (responseCode != HttpURLConnection.HTTP_OK){
                System.out.println("GET request not worked");
            }
            else {
                JSONArray message = apiResponse.getJSONArray("response");

//                System.out.println("message: "+message);

                int i = 0;
                for (Object x : message){
                    JSONObject objects = new JSONObject (x.toString());

                    // get admNo
                    String league_name = objects.getJSONObject ("league").getString("name");
                    String country_name = objects.getJSONObject ("country").getString("name");

                    JSONArray seasons = objects.getJSONArray("seasons");



                    for (Object y : seasons){
                        JSONObject season = new JSONObject(y.toString());

                        int year = season.getInt("year");
                        boolean current = season.getBoolean("current");
                        String current_b = "false";
                        if (current) current_b = "true";

                        JSONObject coverage = season.getJSONObject("coverage");
                        boolean top_scorers = coverage.getBoolean("top_scorers");
                        String top_scorers_b = "false";
                        if (top_scorers) top_scorers_b = "true";
                        boolean injuries = coverage.getBoolean("injuries");
                        String injuries_b = "false";
                        if (injuries) injuries_b = "true";

                        Database db= new Database();
                        db.addFixtures(year, league_name, country_name, current_b, top_scorers_b, injuries_b);

//                        System.out.println("year: "+year);
                    }

                    i += 1;
                }

                System.out.println("DONE");
            }

        }

    }
}