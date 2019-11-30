package com.snowtam.valen.snowtamv0.model;

import com.snowtam.valen.snowtamv0.R;

import java.util.ArrayList;
import java.util.Random;


public class SnotamListDetails {

    Random r = new Random();

    public ArrayList<Snowtam> getList(){
        ArrayList<Snowtam> snowtams = new ArrayList<>();
        snowtams.add(new Snowtam(
                "WSSS",
                "Aéroport international de Singapour Changi",
                "Singapour",
                "Singapour",
                snowtams.size(),
                1.3644,
                103.9915
        ));
        snowtams.add(new Snowtam(
                "RJTT",
                "Aéroport international de Tokyo Haneda",
                "Tokyo",
                "Japon",
                snowtams.size(),
                35.5493,
                139.7798
        ));
        snowtams.add(new Snowtam(
                "RKSI",
                "Aéroport international d'Incheon",
                "Séoul",
                "Corée du Sud",
                snowtams.size(),
                126.4523792,
                37.4478107
        ));
        snowtams.add(new Snowtam(
                "OTHH",
                "Aéroport international de Hamad",
                "Doha",
                "Qatar",
                snowtams.size(),
                25.2608,
                51.6138
        ));
        snowtams.add(new Snowtam(
                "VHHH",
                "Aéroport international de Hong Kong",
                "Hong Kong",
                "Chine",
                snowtams.size(),
                22.308,
                113.9184
        ));
        snowtams.add(new Snowtam(
                "LFPG",
                "Paris Charles De Gaulle",
                "Paris",
                "France",
                snowtams.size(),
                49.0097,
                2.5479
        ));
        return snowtams;
    }
}
