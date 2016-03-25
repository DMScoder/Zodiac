package com.zodiac.Support;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class IO {

    static JSONParser parser;
    static JSONObject jsonObject;

    public static void Open()
    {
        parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("Ship/UnitStats.txt"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
