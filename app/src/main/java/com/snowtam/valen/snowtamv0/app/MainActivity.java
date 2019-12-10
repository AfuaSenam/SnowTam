package com.snowtam.valen.snowtamv0.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.snowtam.valen.snowtamv0.R;
import com.snowtam.valen.snowtamv0.adaptater.StAdapter;
import com.snowtam.valen.snowtamv0.model.Airport;
import com.snowtam.valen.snowtamv0.model.Snowtam2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;

import static android.icu.lang.UCharacter.toUpperCase;

public class MainActivity extends Activity {


    private boolean isOnApiKey = true;
    public static ArrayList<Airport> airports = new ArrayList<>();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    //private String keySnowtam="6fb5e7d0-0c2f-11ea-aaad-bf68a54f0188";   //Utilisée pour tests
    private String keySnowtam="41215a70-1b2b-11ea-9119-0d1c60ba0237"; //Non utilisée
    //private String location="ENBR";
    private String url="https://api.myjson.com/bins/pag12";
    private String urlApi ="https://v4p4sz5ijk.execute-api.us-east-1.amazonaws.com/anbdata/states/notams/notams-realtime-list?api_key="+keySnowtam+"&format=json&criticality=&locations=";//+location;

    private CardView cv;

    private ListView listView;
    private static ArrayList<Snowtam2> snowtams;
    private StAdapter stAdapter;

    private TextView TVnb_snowtam;
    private String Snb_snowtam;

    private EditText ETsearch;

    private String Resnb_snowtam;

    public static final String ID_SNOWTAM = "ID_SNOWTAM";

    private static final int TOAST_DURATION = Toast.LENGTH_SHORT;

    private static final String TAG = "LogPersoMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getJSON();

        listView=(ListView)findViewById(R.id.list_view);
        TVnb_snowtam = (TextView)findViewById(R.id.nb_snowtam);
        ETsearch = (EditText)findViewById(R.id.search);

        ETsearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    AddSnowtam(ETsearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
        snowtams = new ArrayList<>();

        stAdapter = new StAdapter(MainActivity.this, snowtams);
        listView.setAdapter(stAdapter);

        Resnb_snowtam = getString(R.string.nb_snowtam);
        Snb_snowtam = snowtams.size() + " " + Resnb_snowtam;
        TVnb_snowtam.setText(Snb_snowtam);
    }

    public static ArrayList<Snowtam2> getSnowtams(){
        return snowtams;
    }


    private void AddSnowtam(String code) {
        if (code.equals("") || code.length() != 4) {
            String toastTxt = getString(R.string.toast_invalid_snowtam);
            Toast toast = Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
            toast.show();
            return;
        }
        if(Exist(toUpperCase(code))){
            String toastTxt = getString(R.string.toast_existing_snowtam);
            Toast toast = Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
            toast.show();
            return;
        }

        if(isOnApiKey) {
            String request = urlApi + code;
            sendAndRequestResponse(request);
        }else
            sendAndRequestResponse(url);
    }

    public void CreerNouvSnowtam(String response){
        ETsearch.setText("");

        Snowtam2 snowtam = new Snowtam2(response, snowtams.size());
        snowtams.add(0, snowtam);
        stAdapter = new StAdapter(MainActivity.this, snowtams);
        listView.setAdapter(stAdapter);

        Snb_snowtam = snowtams.size() + " " + Resnb_snowtam;
        TVnb_snowtam.setText(Snb_snowtam);

    }

    void onClickLayout(View v){
        Log.i(TAG, "ID : ");
        int id = (int)v.getTag();
        Log.i(TAG, v.getTag().toString());
        Intent intent = new Intent(getApplicationContext(), Infocode.class);
        intent.putExtra(ID_SNOWTAM, id);
        startActivity(intent);
    }

    void onClickButtonPlus(View v){
        AddSnowtam(ETsearch.getText().toString());
    }


    void onClickButtonDelete(View v) {
        int id = (int)v.getTag();
        for(int i = 0; i < snowtams.size(); i++){
            if(id == snowtams.get(i).getId()){
                snowtams.remove(i);
                stAdapter = new StAdapter(MainActivity.this, snowtams);
                listView.setAdapter(stAdapter);
                Snb_snowtam = snowtams.size() + " " + Resnb_snowtam;
                TVnb_snowtam.setText(Snb_snowtam);
                return;
            }
        }
    }

    private void sendAndRequestResponse(String url) {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String jsonresponse = null;
                try {
                    boolean check = true;
                    int i = 0;
                    do{
                        JSONArray JA= new JSONArray(response);
                        JSONObject JO= (JSONObject) JA.get(i++);
                        String JOresponse =(String) JO.get("all");
                        Log.i(TAG, "\nJOresponse (Do While) -> " + JOresponse);
                        if(JOresponse.contains("(SNOWTAM")) {
                            check = false;
                            jsonresponse = JOresponse;
                            Log.i(TAG, "\nlastResponse -> " + jsonresponse);
                        }

                    }while (check);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonresponse != null)
                    CreerNouvSnowtam(jsonresponse);
                else{
                    String toastTxt = getString(R.string.toast_notfound_snowtam);
                    Toast toast = Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 0);
                    toast.show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


    public void getJSON(){
        String json;
        String jsonname = "airports.json";
        try{
            InputStream is = getAssets().open(jsonname);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String OACI = obj.getString("icao");
                if(!OACI.equals("")) {
                    String nom = obj.getString("name");
                    String ville = obj.getString("city");
                    String pays = obj.getString("country");
                    Double lat = Double.parseDouble(obj.getString("lat"));
                    Double lon = Double.parseDouble(obj.getString("lon"));
                    airports.add(new Airport(nom, ville, pays, OACI, lat, lon));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    boolean Exist(String OACI){
        for(int i = 0; i < snowtams.size(); i++){
            if(snowtams.get(i).getOACI().equals(OACI))
                return true;
        }
        return false;
    }
}
