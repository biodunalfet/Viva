package com.devone.hamzafetuga.viva;

/**
 * Created by Hamza Fetuga on 11/9/2014.
 * This class contains the Drug model for each of the included Malaria drugs
 * The "Name" property contains the name of the drug
 * The "Dosage" property contains the array that shows the dosages of the drug
 * The "Timing" property contains the time interval between each dosage
 */
public class Drug {
    String Name;
    int[] Dosage;
    int[] Timing;
    int DosageLength;


    public Drug(String Name, int[] Dosage, int[] Timing, int dosageLength )
    {
        this.Name=Name;
        this.Dosage= Dosage;
        this.Timing= Timing;
        this.DosageLength = dosageLength;
    }

    public Drug(int x)
    {
        Drug z = MalariaDrugs.malariaDrugs[x];

        this.Name= z.getName();
        this.Dosage= z.getDosage();
        this.Timing= z.getTiming();
        this.DosageLength = z.getDosageLength();
    }

    public int getDosageLength() {
        return DosageLength;
    }

    public int[] getDosage()
    {
        return this.Dosage;
    }

    public void setDosage(int[] x)
    {
        this.Dosage=x;
    }

    public void setTiming(int[] y)
    {
        this.Timing= y;
    }

    public int[] getTiming()
    {
        return this.Timing;
    }

    public String getName()
    {
        return this.Name;
    }

    public void setName(String z)
    {
        this.Name= z;
    }




}
