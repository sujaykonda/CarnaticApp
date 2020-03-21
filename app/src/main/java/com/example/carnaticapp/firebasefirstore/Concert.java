package com.example.carnaticapp.firebasefirstore;

import android.icu.util.DateInterval;

public class Concert {
    public DateInterval time;
    public String[] neededAcc;
    public String[] allAcc;
    public Concert(DateInterval time, String[] neededAcc, String[] allAcc){
        this.time = time;
        this.neededAcc = neededAcc;
        this.allAcc = allAcc;
    }

}
