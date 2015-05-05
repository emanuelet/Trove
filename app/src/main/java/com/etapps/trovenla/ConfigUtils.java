package com.etapps.trovenla;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by emanuele on 5/05/15.
 */
public class ConfigUtils {


    public static String getUrl(String property){
        Properties properties = new Properties();
        InputStream inputStream = null;
        String value="";
        try {
            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
            value=properties.getProperty(property);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
}