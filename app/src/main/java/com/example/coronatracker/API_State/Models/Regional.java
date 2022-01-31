package com.example.coronatracker.API_State.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Regional {


    @SerializedName("loc")
    @Expose
    private String loc;
    @SerializedName("confirmedCasesIndian")
    @Expose
    private Integer confirmedCasesIndian;
    @SerializedName("confirmedCasesForeign")
    @Expose
    private Integer confirmedCasesForeign;
    @SerializedName("discharged")
    @Expose
    private Integer discharged;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("totalConfirmed")
    @Expose
    private Integer totalConfirmed;

    public Regional(String loc, Integer confirmedCasesIndian, Integer confirmedCasesForeign, Integer discharged, Integer deaths, Integer totalConfirmed) {
        this.loc = loc;
        this.confirmedCasesIndian = confirmedCasesIndian;
        this.confirmedCasesForeign = confirmedCasesForeign;
        this.discharged = discharged;
        this.deaths = deaths;
        this.totalConfirmed = totalConfirmed;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Integer getConfirmedCasesIndian() {
        return confirmedCasesIndian;
    }

    public void setConfirmedCasesIndian(Integer confirmedCasesIndian) {
        this.confirmedCasesIndian = confirmedCasesIndian;
    }

    public Integer getConfirmedCasesForeign() {
        return confirmedCasesForeign;
    }

    public void setConfirmedCasesForeign(Integer confirmedCasesForeign) {
        this.confirmedCasesForeign = confirmedCasesForeign;
    }

    public Integer getDischarged() {
        return discharged;
    }

    public void setDischarged(Integer discharged) {
        this.discharged = discharged;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(Integer totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }
}
