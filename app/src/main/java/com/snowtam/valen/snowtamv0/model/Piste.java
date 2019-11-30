package com.snowtam.valen.snowtamv0.model;

public class Piste {

    private final static String SEPARATEUR = "/";

    // C)
    private int idPiste;
    private final static String RUNWAY = "RUNWAY";
    // D)
    //private int longDeb;
    //private final static String LONG_DEB = "CLEARED RUNWAY LENGTH";
    // E)
    //private int largDeb;
    //private final static String LARG_DEB = "CLEARED RUNWAY WIDTH";
    //private final static String UNITE_DEB = "M";
    //private String largDebCote;
    //private final static String[] LARG_DEB_COTE = {"LEFT", "RIGHT"};
    // F)
    private int[] condsInt;
    private static final String[] CONDS = {"CLEAR AND DRY", "DAMP", "WET", "RIME", "DRY SNOW", "WET SNOW", "SLUSH", "ICE", "COMPACTED", "FROZEN"};
    // G)
    private int[] epaisseurs;
    private final static String MEAN_DEPTH = "MEAN DEPTH";
    private final static String UNITE_EPAISSEUR = "mm";
    //H)
    private int[] coeff;
    private  final static String BRAKING_ACTION = "BRAKING ACTION";
    private final static String[] COEFF = {"X", "POOR", "MEDIUM TO POOR", "MEDIUM", "MEDIUM TO GOOD", "GOOD"};

    private String C, D, E, F, G, H;
    private String decode;

    public Piste(String C, String F, String G, String H){
        int i;
        idPiste = Integer.parseInt(C);
        this.C = RUNWAY + idPiste;

        condsInt = DecodeFGH(F);
        for(i = 0; i < condsInt.length; i++){
            this.F += CONDS[condsInt[i]];
            if(i < condsInt.length - 1)
                this.F += " " + SEPARATEUR + " ";
        }

        this.G = MEAN_DEPTH + " ";
        epaisseurs = DecodeFGH(G);
        for(i = 0; i < epaisseurs.length; i++){
            this.G += epaisseurs[i] + UNITE_EPAISSEUR;
            if(i < epaisseurs.length - 1)
                this.G += " " + SEPARATEUR + " ";
        }

        this.H = BRAKING_ACTION + " : ";
        coeff = DecodeFGH(H);
        for(i = 0; i < coeff.length; i++){
            this.H += COEFF[coeff[i]];
            if(i < coeff.length - 1)
                this.H += " " + SEPARATEUR + " ";
        }

        DecodeAll();
    }

    public String getDecode(){
        return decode;
    }

    private void DecodeAll(){
        int i;
        decode = "C) " + C + "\n";
        decode = "F) " + F + "\n";
        decode = "G) " + G + "\n";
        decode = "H) " + H;
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

    private int[] DecodeFGH(String X){
        int l = compterOccurrences(X, SEPARATEUR) + 1;
        int[] tab = new int[l];
        String maChaine = X.substring(X.indexOf(")") + 1);
        String val;

        for(int i = 0; i < l; i++){
            val = maChaine.substring(0, maChaine.indexOf(SEPARATEUR));
            if(val.contains("X"))
                tab[i] = -1;
            else{
                tab[i] = Integer.parseInt(val);
                maChaine = maChaine.substring(X.indexOf(SEPARATEUR) + 1);
            }
        }
        return tab;

    }

}
