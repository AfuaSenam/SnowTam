package com.snowtam.valen.snowtamv0.model;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.snowtam.valen.snowtamv0.app.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class DataJson extends AsyncTask<String,Void, String> {

    private String code;
    private String decode;

    public DataJson(String code) {
        this.code = code;
    }

    public String getCode() {
        return decode;
    }

    @Override
    protected String doInBackground(String... strings) {
        //return readJson(strings[0]);
        String snowtam = "UNDEFINED";
        URL url=null;
        HttpURLConnection urlConnection=null;
        String response;
        StringBuilder builder=null;

        try {

            url=new URL(strings[0]);

            urlConnection=(HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();
            if (code !=  200) {
                //Log.i(TAG, strings[0]);
                throw new IOException("Invalid response from server: " + code);

            }
            urlConnection.connect();
            int responceCode = urlConnection.getResponseCode();
            Log.i(TAG, strings[0]);


            if (responceCode == HttpURLConnection.HTTP_OK) {
                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONArray JA= new JSONArray(builder.toString());
                Log.i(TAG, builder.toString());
                JSONObject JO= (JSONObject) JA.get(0);
                String traitementText=(String) JO.get("all");

                snowtam= traitementText;

                //snowtam = String.valueOf(main.getDouble("temp"));
            }
            else
            {
                snowtam = "";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
                //weather = "nop";
            }
        }
        return snowtam;


    }

    @Override
    protected void onPostExecute(String temp) {
        //MainActivity.setLastResponse(temp);
    }
}
