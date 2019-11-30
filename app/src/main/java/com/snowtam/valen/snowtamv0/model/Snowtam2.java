package com.snowtam.valen.snowtamv0.model;

import java.util.ArrayList;
import java.util.Date;

public class Snowtam2 {

    // https://gist.github.com/tdreyno/4278655

    private String code;

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



    public Snowtam2(String code) {
        this.code = code;
        OACI = code.substring(code.indexOf(")") + 1, code.indexOf("\n"));
        code = code.substring(code.indexOf("\n") + 1);
        date = DecodeB(code);
        code = code.substring(code.indexOf("\n") + 1);

        decode = OACI + "\n";
        decode += date + "\n";
        decode += "\n";

        int nbC = compterOccurrences(code, "C) ");
        String C, F, G, H;
        Piste piste;
        pistes = new ArrayList<Piste>();
        for(int i = 0; i < nbC; i++){
            C = code.substring(code.indexOf("C"), code.indexOf(" "));
            code = code.substring(code.indexOf(" "));
            F = code.substring(code.indexOf("F"), code.indexOf(" "));
            code = code.substring(code.indexOf(" "));
            G = code.substring(code.indexOf("G"), code.indexOf(" "));
            code = code.substring(code.indexOf(" "));
            H = code.substring(code.indexOf("H"), code.indexOf(" "));
            code = code.substring(code.indexOf(" "));
            piste = new Piste(C, F, G, H);
            pistes.add(piste);
            decode += piste.getDecode() + "\n";
            decode += "\n";
        }


    }

    private String DecodeB(String X){
        code = code.substring(code.indexOf(")") + 1);
        String mois = MONTHS[Integer.parseInt(code.substring(0, 2)) - 1];
        code = code.substring(2);
        String jour = code.substring(0, 2);
        code = code.substring(2);
        String heure = code.substring(0, 2);
        code = code.substring(2);
        String min = code.substring(0, 2);
        return jour + " " + mois + " AT " + heure + "h" + min;
    }

    public String getCode() {
        return code;
    }

    public String getDecode() {
        return decode;
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
}
