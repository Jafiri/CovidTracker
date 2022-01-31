package com.example.coronatracker.API_State.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary {


    @SerializedName("total")
    @Expose
    private Integer total;
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
    @SerializedName("confirmedButLocationUnidentified")
    @Expose
    private Integer confirmedButLocationUnidentified;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public Integer getConfirmedButLocationUnidentified() {
        return confirmedButLocationUnidentified;
    }

    public void setConfirmedButLocationUnidentified(Integer confirmedButLocationUnidentified) {
        this.confirmedButLocationUnidentified = confirmedButLocationUnidentified;
    }
}
