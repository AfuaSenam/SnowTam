package com.snowtam.valen.snowtamv0.model;

import android.nfc.Tag;
import android.util.Log;

import com.snowtam.valen.snowtamv0.app.MainActivity;

import java.util.ArrayList;
import java.util.Date;

public class Snowtam2 {

    private static String TAG = "Snowtam2Class";
    private int space = 2; //3 espace, 2 sans espace

    private int id;

    // https://gist.github.com/tdreyno/4278655

    private String code;

    private Airport airport;

    // A)
    private String OACI;
    private String emplacement;
    // B)
    private String date;
    private static final String[] MONTHS = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    // C) -> D)
    private ArrayList<Piste> pistes;

    private String A, B;
    private String decode;

    public Snowtam2(String code, int id) {
        this.id = id;
        //this.OACI = OACI;
        this.code = code;

        if(code.contains("A) "))
            space = 3;
        OACI = code.substring(code.indexOf("A)") + space, code.indexOf("B)") - 1);

        airport = getAirportByOACI(OACI);
        if(airport == null)
            airport = new Airport("undefine", "undefine", "undefine", OACI, (double)0, (double)0);

        date = DecodeB(code.substring(code.indexOf("B)") + space, code.indexOf("C)") - 1));

        decode = "A) " + OACI + " - " + airport.getNom() + "\n";
        decode += "B) " + date + "\n";
        decode += "\n";


        int nbC = compterOccurrences(code, "C) ");
        String C, F, G, H;
        Piste piste;
        pistes = new ArrayList<>();
        for(int i = 0; i < nbC; i++){
            C = code.substring(code.indexOf("C)") + space, code.indexOf("F)") - 1);
            F = code.substring(code.indexOf("F)") + space, code.indexOf("G)") - 1);
            G = code.substring(code.indexOf("G)") + space, code.indexOf("H)") - 1);
            H = code.substring(code.indexOf("H)") + space, code.indexOf("N)") - 1);
            piste = new Piste(C, F, G, H);
            pistes.add(piste);
            decode += piste.getDecode() + "\n";
            decode += "\n";
        }
        Log.i(TAG, "decode -> " + decode);


    }

    private String DecodeB(String X){
        Log.i(TAG, "X -> " + X);
        Log.i(TAG, "Integer.parseInt(X.substring(0, 2)) - 1 -> " + (Integer.parseInt(X.substring(0, 2)) - 1));
        String mois = MONTHS[Integer.parseInt(X.substring(0, 2)) - 1];
        X = X.substring(2);
        String jour = X.substring(0, 2);
        X = X.substring(2);
        String heure = X.substring(0, 2);
        X = X.substring(2);
        String min = X.substring(0, 2);
        return jour + " " + mois + " AT " + heure + "h" + min;
    }

    public String getOACI() {
        return OACI;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDecode() {
        return decode;
    }

    public Airport getAirport() {
        return airport;
    }

    private int compterOccurrences(String maChaine, String recherche)
    {
        if(!maChaine.contains(recherche))
            return 0;

        int nb = 0;
        while (maChaine.indexOf(recherche) > 0){
            nb++;
            maChaine = maChaine.substring(maChaine.indexOf(recherche) + 1);
        }
        return nb;
    }




    public Airport getAirportByOACI(String OACI) {
        Airport airport = null;
        for(int i = 0; i < MainActivity.airports.size(); i++){
            if(MainActivity.airports.get(i).getOACI().equals(OACI))
                return MainActivity.airports.get(i);
        }
        return airport;
    }
}
