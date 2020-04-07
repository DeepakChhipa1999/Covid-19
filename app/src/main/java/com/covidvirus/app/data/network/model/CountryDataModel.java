package com.covidvirus.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CountryDataModel implements Serializable {
    @Expose
    @SerializedName("country")
    private String country;

    @Expose
    @SerializedName("cases")
    private long nbrCases;

    @Expose
    @SerializedName("todayCases")
    private long todayCases;

    @Expose
    @SerializedName("deaths")
    private long nbrDeath;

    @Expose
    @SerializedName("todayDeaths")
    private long todayDeaths;

    @Expose
    @SerializedName("recovered")
    private long nbrRecovered;

    @Expose
    @SerializedName("active")
    private long nbrActiveCases;

    @Expose
    @SerializedName("critical")
    private long nbrCriticalCases;

    @Expose
    @SerializedName("casesPerOneMillion")
    private double nbrCasesPerOneMillion;

    public CountryDataModel(String country, long nbrCases,
                            long todayCases, long nbrDeath,
                            long todayDeaths, long nbrRecovered,
                            long nbrActiveCases, long nbrCriticalCases,
                            double nbrCasesPerOneMillion) {
        this.country = country;
        this.nbrCases = nbrCases;
        this.todayCases = todayCases;
        this.nbrDeath = nbrDeath;
        this.todayDeaths = todayDeaths;
        this.nbrRecovered = nbrRecovered;
        this.nbrActiveCases = nbrActiveCases;
        this.nbrCriticalCases = nbrCriticalCases;
        this.nbrCasesPerOneMillion = nbrCasesPerOneMillion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getNbrCases() {
        return nbrCases;
    }

    public void setNbrCases(long nbrCases) {
        this.nbrCases = nbrCases;
    }

    public long getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(long todayCases) {
        this.todayCases = todayCases;
    }

    public long getNbrDeath() {
        return nbrDeath;
    }

    public void setNbrDeath(long nbrDeath) {
        this.nbrDeath = nbrDeath;
    }

    public long getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(long todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public long getNbrRecovered() {
        return nbrRecovered;
    }

    public void setNbrRecovered(long nbrRecovered) {
        this.nbrRecovered = nbrRecovered;
    }

    public long getNbrActiveCases() {
        return nbrActiveCases;
    }

    public void setNbrActiveCases(long nbrActiveCases) {
        this.nbrActiveCases = nbrActiveCases;
    }

    public long getNbrCriticalCases() {
        return nbrCriticalCases;
    }

    public void setNbrCriticalCases(long nbrCriticalCases) {
        this.nbrCriticalCases = nbrCriticalCases;
    }

    public double getNbrCasesPerOneMillion() {
        return nbrCasesPerOneMillion;
    }

    public void setNbrCasesPerOneMillion(double nbrCasesPerOneMillion) {
        this.nbrCasesPerOneMillion = nbrCasesPerOneMillion;
    }
}
