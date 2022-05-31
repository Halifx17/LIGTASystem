package com.example.ligtasystem;

public class ModelStat {

    /*
    {
        "updated": 1654020168085,
        "country": "Afghanistan",

        "countryInfo": {
            "_id": 4,
            "iso2": "AF",
            "iso3": "AFG",
            "lat": 33,
            "long": 65,
            "flag": "https://disease.sh/assets/img/flags/af.png"
        },

        "cases": 180347,
        "todayCases": 84,
        "deaths": 7705,
        "todayDeaths": 2,
        "recovered": 163072,
        "todayRecovered": 42,
        "active": 9570,
        "critical": 1124,
        "casesPerOneMillion": 4442,
        "deathsPerOneMillion": 190,
        "tests": 967482,
        "testsPerOneMillion": 23830,
        "population": 40599056,
        "continent": "Asia",
        "oneCasePerPeople": 225,
        "oneDeathPerPeople": 5269,
        "oneTestPerPeople": 42,
        "activePerOneMillion": 235.72,
        "recoveredPerOneMillion": 4016.65,
        "criticalPerOneMillion": 27.69
    }
     */

    String country, cases, todayCases, deaths, todayDeaths, recovered, todayRecovered;

    public ModelStat() {

    }

    public ModelStat(String country, String cases, String todayCases, String deaths, String todayDeaths, String recovered, String todayRecovered) {
        this.country = country;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(String todayRecovered) {
        this.todayRecovered = todayRecovered;
    }
}
