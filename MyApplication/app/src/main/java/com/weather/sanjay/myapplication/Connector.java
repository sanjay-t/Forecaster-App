package com.weather.sanjay.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connector extends AsyncTask<String , Void, JSONObject >{

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result;
    URL urlObj;
    JSONObject jObj = null;
    String st;
    String street_address, city, state_select, radioTemp, action="test";
    public Connector(String s, String s1, String s2, String s3){
        street_address=s;
        city=s1;
        state_select=s2;
        radioTemp=s3;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        String ds = "Step1",str = "Step2";
        String url = new String("http://sanjayt.elasticbeanstalk.com/index.php/?");
        url+="street_address="+street_address+"&city="+city+"&state_select="+state_select+"&radioTemp="+radioTemp+"&action=test";
        url=url.replaceAll("\\s","+");
        Log.d("wolulululuullulululuul", url);
        try {
            urlObj = new URL(url);

            conn = (HttpURLConnection) urlObj.openConnection();
            Log.d(str, "Made Connection");
            conn.setDoOutput(false);

            conn.setRequestMethod("GET");


            conn.setRequestProperty("Accept-Charset", charset);

            conn.setConnectTimeout(15000);
            Log.d(ds, "StepN");
            conn.connect();
            Log.d(ds,"Step2");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            Log.d(ds,"Step3");
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;
    }
}