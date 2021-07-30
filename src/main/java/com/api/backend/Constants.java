package com.api.backend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {
    String result = "";
    InputStream inputStream;

    public static String seasons_endpoint = "https://api-football-v1.p.rapidapi.com/v3/standings?season=2020&league=39";
    public static String rapid_apiKey = "407f57c6c3msh39a5081824ca9eep1f6f52jsn6a9a6e410883";

    public String getPropValues(String propertyKey) throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath. ");
            }

            //get the property value and print it out
            result = prop.getProperty(propertyKey);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
        return result;
    }
}
