package com.snowtam.valen.snowtamv0.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snowtam.valen.snowtamv0.R;
import com.snowtam.valen.snowtamv0.adaptater.StAdapter;
import com.snowtam.valen.snowtamv0.model.OnSwipeTouchListener;
import com.snowtam.valen.snowtamv0.model.Snowtam;

import java.util.ArrayList;

public class Infocode extends Activity {

    private int id;
    public static final String ID_SNOWTAM = "ID_SNOWTAM";
    private ArrayList<Snowtam> snowtams;
    private Snowtam selectedSnowtam;

    private TextView TVsnowtam_code;
    private TextView TVsnowtam_name;
    private TextView TVsnowtam_city;
    private TextView TVsnowtam_country;
    private TextView TVsnowtam_lat;
    private TextView TVsnowtam_lon;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infocode);

        view = (View)findViewById(R.id.view);

        view.setOnTouchListener(new OnSwipeTouchListener(Infocode.this) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                int i = snowtams.indexOf(selectedSnowtam) - 1;
                if(i < 0)
                    i = snowtams.size() - 1;
                selectedSnowtam = snowtams.get(i);
                MAJAffSnowtam();
            }
            public void onSwipeLeft() {
                int i = (snowtams.indexOf(selectedSnowtam) + 1) % snowtams.size();
                selectedSnowtam = snowtams.get(i);
                MAJAffSnowtam();

            }
            public void onSwipeBottom() {
                Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
                intent.putExtra(ID_SNOWTAM, id);
                startActivity(intent);
            }

        });

        TVsnowtam_code = (TextView)findViewById(R.id.snowtam_code);
        TVsnowtam_name = (TextView)findViewById(R.id.snowtam_name);
        TVsnowtam_city = (TextView)findViewById(R.id.snowtam_city);
        TVsnowtam_country = (TextView)findViewById(R.id.snowtam_country);
        TVsnowtam_lat = (TextView)findViewById(R.id.snowtam_lat);
        TVsnowtam_lon = (TextView)findViewById(R.id.snowtam_lon);

        snowtams = MainActivity.getSnowtams();
        Intent intent = getIntent();
        id = intent.getIntExtra(MainActivity.ID_SNOWTAM, 0);

        for(int i = 0; i < snowtams.size(); i++){
            if(id == snowtams.get(i).getId()){
                selectedSnowtam = snowtams.get(i);
                i = snowtams.size();
            }
        }

        MAJAffSnowtam();

        String toastTxt = "SWIPE\n" + "gauche/droite -> suivant/précédent\n" + "bas -> plan";
        Toast toast = Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();

    }

    private void MAJAffSnowtam(){
        id = selectedSnowtam.getId();
        TVsnowtam_code.setText(selectedSnowtam.getOACI());
        TVsnowtam_name.setText(selectedSnowtam.getName());
        TVsnowtam_city.setText(selectedSnowtam.getCity());
        TVsnowtam_country.setText(selectedSnowtam.getCountry());
        TVsnowtam_lat.setText("" + selectedSnowtam.getLat());
        TVsnowtam_lon.setText("" + selectedSnowtam.getLon());

    }

    void onClickBack(View v){
        finish();
    }
}
