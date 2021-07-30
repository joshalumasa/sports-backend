package com.api.backend;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class ApiCalls {
    Constants property;

    public void fetchSeasons() throws Exception {
        String url = Constants.seasons_endpoint;

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //request headers
        httpClient.setRequestMethod("GET");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        httpClient.setRequestProperty("x-rapidapi-key", "407f57c6c3msh39a5081824ca9eep1f6f52jsn6a9a6e410883");
        httpClient.setRequestProperty("x-rapidapi-host", "api-football-v1.p.rapidapi.com");

        int responseCode = httpClient.getResponseCode();

        System.out.println("responseCode: " + responseCode);

        InputStream result;

        if (responseCode < HttpsURLConnection.HTTP_BAD_REQUEST) {
            result = httpClient.getInputStream();
        } else {
            result = httpClient.getErrorStream();
        }

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(result)
        )) {
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            JSONObject apiResponse = new JSONObject(response.toString());

            if (responseCode != HttpsURLConnection.HTTP_OK) {
                System.out.println("GET request could not be completed");
            } else {
                JSONArray message = apiResponse.getJSONArray("response");

                int i = 0;
                for (Object x : message) {
                    JSONObject objects = new JSONObject(x.toString());

                    String league_name = objects.getJSONObject("league").getString("name");
                    String country_name = objects.getJSONObject("league").getString("country");
                    int season = objects.getJSONObject("league").getInt("season");


                    JSONObject standings = new JSONObject(property.toString());

                    int rank = standings.getJSONObject("standings").getInt("rank");
                    Database db = new Database();

                    JSONObject team = new JSONObject(property.toString());
                    String team_name = standings.getJSONObject("team").getString("name");

                    JSONObject points = standings.getJSONObject("points");

//                        JSONObject coverage = season.getJSONObject("coverage");
//                        boolean top_scorers = coverage.getBoolean("top_scorers");
//                        String top_scorers_b = "false";
//                        if (top_scorers) top_scorers_b = "true";
//                        boolean injuries = coverage.getBoolean("injuries");
//                        String injuries_b = "false";
//                        if (injuries) injuries_b = "true";

                        db.addSeasons(league_name, country_name, season, rank, team_name, points);


                    i += 1;
                }
                System.out.println("DONE");
            }
        }
    }
}
