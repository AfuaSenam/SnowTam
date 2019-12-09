package com.snowtam.valen.snowtamv0.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snowtam.valen.snowtamv0.R;
import com.snowtam.valen.snowtamv0.adaptater.StAdapter;
import com.snowtam.valen.snowtamv0.model.OnSwipeTouchListener;
import com.snowtam.valen.snowtamv0.model.Snowtam;
import com.snowtam.valen.snowtamv0.model.Snowtam2;

import java.util.ArrayList;

import static java.lang.Math.min;

public class Infocode extends Activity {

    private int id;
    private boolean isDecode = true;
    public static final String ID_SNOWTAM = "ID_SNOWTAM";
    private ArrayList<Snowtam2> snowtams;
    private Snowtam2 selectedSnowtam;

    private TextView TVsnowtam_code;
    private Button button_code;
    private LinearLayout layout_swipe;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    /*
    private TextView TVsnowtam_name;
    private TextView TVsnowtam_city;
    private TextView TVsnowtam_country;
    private TextView TVsnowtam_lat;
    private TextView TVsnowtam_lon;
    */

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
                MAJLayoutSwipe();
            }
            public void onSwipeLeft() {
                int i = (snowtams.indexOf(selectedSnowtam) + 1) % snowtams.size();
                selectedSnowtam = snowtams.get(i);
                MAJAffSnowtam();
                MAJLayoutSwipe();

            }
            public void onSwipeBottom() {

            }

        });

        TVsnowtam_code = (TextView)findViewById(R.id.snowtam_code);
        button_code = (Button)findViewById(R.id.button_code);
        layout_swipe = (LinearLayout)findViewById(R.id.layout_swipe);

        snowtams = MainActivity.getSnowtams();
        Intent intent = getIntent();
        id = intent.getIntExtra(MainActivity.ID_SNOWTAM, 0);

        for(int i = 0; i < snowtams.size(); i++){
            if(id == snowtams.get(i).getId()){
                selectedSnowtam = snowtams.get(i);
                i = snowtams.size();
            }
        }

        InitLayoutSwipe();
        MAJAffSnowtam();

        /*
        String toastTxt = "SWIPE\n" + "gauche/droite -> suivant/précédent\n" + "bas -> plan";
        Toast toast = Toast.makeText(getApplicationContext(), toastTxt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
        */

    }

    private void MAJAffSnowtam(){
        id = selectedSnowtam.getId();
        if(isDecode){
            TVsnowtam_code.setText(selectedSnowtam.getDecode());
            return;
        }
        TVsnowtam_code.setText(selectedSnowtam.getCode());

    }

    void onClickBack(View v){
        finish();
    }

    void onClickCode(View v){
        if(isDecode){
            isDecode = false;
            button_code.setText("Decode");
            TVsnowtam_code.setText(selectedSnowtam.getCode());
            return;
        }
        isDecode = true;
        button_code.setText("Code");
        TVsnowtam_code.setText(selectedSnowtam.getDecode());

    }

    void onClickPlan(View v){
        Intent intent = new Intent(getApplicationContext(), GoogleMapsActivity.class);
        intent.putExtra(ID_SNOWTAM, id);
        startActivity(intent);
    }

    void InitLayoutSwipe(){
        int n = 3;
        if(snowtams.size() < 3){
            n = snowtams.size();
        }
        for(int i = 0; i < n; i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.swipe_unselec);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    0.1f
            );
            imageView.setLayoutParams(layoutParams);
            layout_swipe.addView(imageView);
            imageViews.add(imageView);
        }
        MAJLayoutSwipe();


    }

    void MAJLayoutSwipe(){

        for(int i = 0; i < imageViews.size(); i++){
            imageViews.get(i).setImageResource(R.mipmap.swipe_unselec);
        }
        if(snowtams.indexOf(selectedSnowtam) == 0){
            imageViews.get(0).setImageResource(R.mipmap.swipe_selec);
            return;
        }
        if(snowtams.indexOf(selectedSnowtam) == snowtams.size()-1){
            imageViews.get(min(2, snowtams.size()-1)).setImageResource(R.mipmap.swipe_selec);
            return;
        }
        imageViews.get(1).setImageResource(R.mipmap.swipe_selec);

    }
}
