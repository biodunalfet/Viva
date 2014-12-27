package com.devone.hamzafetuga.viva;

public class User {

    static Drug Drug;
    boolean[] hasTakenDrug;

    public User (Drug drug) {

        this.Drug = drug;
        this.hasTakenDrug = new boolean[Drug.DosageLength];
    }

    public void setElementOfHasTakenDrug(int index, boolean value) {
        this.hasTakenDrug[index]= value;
    }
}
