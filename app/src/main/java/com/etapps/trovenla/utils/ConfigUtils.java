package com.etapps.trovenla.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by emanuele on 5/05/15.
 */
public class ConfigUtils {


    public static String getUrl(String property, Context mContext) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        String value = "";
        Resources resources = mContext.getResources();
        AssetManager assetManager = resources.getAssets();

        // Read from the /assets directory
        try {
            inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            value = properties.getProperty(property);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
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