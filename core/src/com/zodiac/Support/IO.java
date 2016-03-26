package com.zodiac.Support;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.Externalizable;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by SYSTEM on 3/10/2016.
 */
public class IO {

    static JSONParser parser;
    static JSONObject jsonObject;
    static FileReader reader;
    static ArrayList<JSONObject> units = new ArrayList<JSONObject>();

    public static void Open()
    {
        parser = new JSONParser();
        try {
            reader = new FileReader("Ship/UnitStats.txt");
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator i = array.iterator();
            while(i.hasNext())
            {
                units.add((JSONObject) i.next());
            }
            sortJSON(units,0,units.size()-1);
        }catch (Exception e){e.printStackTrace();}
    }

    public static void printList()
    {
        for(int i=0;i<units.size();i++)
            System.out.println(units.get(i).get("Name"));
    }

    public static long getStat(String name, String stat)
    {
        JSONObject unit = binarySearchJSON(name);
        return (unit==null?0:((long) unit.get(stat)));
    }

    public static JSONObject binarySearchJSON(String string)
    {
        int lo = 0;
        int hi = units.size()-1;
        while(hi>=lo)
        {
            int mid = (hi+lo)/2;
            if(units.get(mid).get("Name").equals(string))
                return units.get(mid);

            else if(((String)units.get(mid).get("Name")).compareTo(string)<0)
                lo = mid+1;

            else
                hi = mid-1;
        }
        return null;
    }

    public static void sortJSON(ArrayList<JSONObject> array, int lo, int hi)
    {
        if(lo<hi)
        {
            int p = partition(array,lo,hi);
            sortJSON(array,lo,p-1);
            sortJSON(array,p+1,hi);
        }
    }

    public static int partition(ArrayList<JSONObject> array, int lo, int hi)
    {
        String pivot = (String)array.get(hi).get("Name");
        int i = lo;
        for(int j=lo;j<hi;j++)
        {
            if(((String)array.get(j).get("Name")).compareTo(pivot)<0)
            {
                swap(array,i,j);
                i++;
            }
        }
        swap(array,i,hi);
        return i;
    }

    public static void swap(ArrayList<JSONObject> array, int a, int b)
    {
        JSONObject temp = array.get(a);
        array.set(a,array.get(b));
        array.set(b,temp);
    }
}
