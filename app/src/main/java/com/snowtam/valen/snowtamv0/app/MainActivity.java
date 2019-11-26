package com.snowtam.valen.snowtamv0.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snowtam.valen.snowtamv0.R;
import com.snowtam.valen.snowtamv0.adaptater.StAdapter;
import com.snowtam.valen.snowtamv0.model.OnSwipeTouchListener;
import com.snowtam.valen.snowtamv0.model.SnotamListDetails;
import com.snowtam.valen.snowtamv0.model.Snowtam;

import java.util.ArrayList;

public class MainActivity extends Activity {


    private ListView listView;
    private static ArrayList<Snowtam> snowtams;
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

        SnotamListDetails snotamListDetails = new SnotamListDetails();
        snowtams = snotamListDetails.getList();


        stAdapter = new StAdapter(MainActivity.this, snowtams);
        listView.setAdapter(stAdapter);

        Resnb_snowtam = getString(R.string.nb_snowtam);
        Snb_snowtam = snowtams.size() + " " + Resnb_snowtam;
        TVnb_snowtam.setText(Snb_snowtam);
    }

    public static ArrayList<Snowtam> getSnowtams(){
        return snowtams;
    }

    private void AddSnowtam(String code){
        if(code.equals("") || code.length() != 4){
            String toastTxt = "Code invalide\n" + "(4 caractères)";
            Toast toast = Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 0);
            toast.show();
            return;
        }

        Snowtam snowtam = new Snowtam(
                code,
                "ENSIM",
                "Le Mans",
                "France",
                snowtams.size(),
                "48,0189222",
                "0,1575925"
        );
        ETsearch.setText("");
        snowtams.add(0, snowtam);
        stAdapter = new StAdapter(MainActivity.this, snowtams);
        listView.setAdapter(stAdapter);

        Snb_snowtam = snowtams.size() + " " + Resnb_snowtam;
        TVnb_snowtam.setText(Snb_snowtam);

    }

    void onClickLayout(View v){
        int id = (int)v.getTag();
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
}
