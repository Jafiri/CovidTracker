package com.example.coronatracker.API_State.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("summary")
    @Expose
    private Summary summary;
    @SerializedName("unofficial-summary")
    @Expose
    private List<UnofficialSummary> unofficialSummary = null;
    @SerializedName("regional")
    @Expose
    private List<Regional> regional = null;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<UnofficialSummary> getUnofficialSummary() {
        return unofficialSummary;
    }

    public void setUnofficialSummary(List<UnofficialSummary> unofficialSummary) {
        this.unofficialSummary = unofficialSummary;
    }

    public List<Regional> getRegional() {
        return regional;
    }

    public void setRegional(List<Regional> regional) {
        this.regional = regional;
    }
}
