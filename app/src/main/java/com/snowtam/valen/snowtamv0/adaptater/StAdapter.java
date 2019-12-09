package com.snowtam.valen.snowtamv0.adaptater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snowtam.valen.snowtamv0.R;
import com.snowtam.valen.snowtamv0.model.Snowtam;
import com.snowtam.valen.snowtamv0.model.Snowtam2;

import java.util.ArrayList;

public class StAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Snowtam2> snowtams;

    public StAdapter(Context context, ArrayList<Snowtam2> snowtams) {
        this.context = context;
        this.snowtams = snowtams;
    }

    @Override
    public int getCount() {
        return snowtams.size();
    }

    @Override
    public Object getItem(int position) {
        return snowtams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context, R.layout.list_items, null);
        }

        /*
        TextView name = (TextView)convertView.findViewById(R.id.snotamname);
        TextView city = (TextView)convertView.findViewById(R.id.snotamcity);
        TextView country = (TextView)convertView.findViewById(R.id.snotamcountry);
        Button id = (Button)convertView.findViewById(R.id.snotamid);
        LinearLayout idL = (LinearLayout)convertView.findViewById(R.id.clickableLayout);

        Snowtam snowtam = snowtams.get(position);
        name.setText(snowtam.getName());
        city.setText(snowtam.getCity());
        country.setText(snowtam.getCountry());
        id.setTag(snowtam.getId());
        idL.setTag(snowtam.getId());
        */


        TextView name = (TextView)convertView.findViewById(R.id.snotamname);
        TextView city = (TextView)convertView.findViewById(R.id.snotamcity);
        TextView country = (TextView)convertView.findViewById(R.id.snotamcountry);
        Button id = (Button)convertView.findViewById(R.id.snotamid);
        LinearLayout idL = (LinearLayout)convertView.findViewById(R.id.clickableLayout);
        Snowtam2 snowtam = snowtams.get(position);

        String nameAff =  snowtam.getOACI();
        if(!snowtam.getAirport().getNom().equals("undefine"))
            nameAff +=  " - " + snowtam.getAirport().getNom();

        name.setText(nameAff);
        city.setText(snowtam.getAirport().getVille());
        country.setText(snowtam.getAirport().getPays());
        id.setTag(snowtam.getId());
        idL.setTag(snowtam.getId());


        return convertView;
    }
}
