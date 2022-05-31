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



    {
            "ID": "13399773-6d3d-4cbc-a9fe-e8169fbccc76",
            "Country": "Afghanistan",
            "CountryCode": "AF",
            "Slug": "afghanistan",
            "NewConfirmed": 0,
            "TotalConfirmed": 180259,
            "NewDeaths": 0,
            "TotalDeaths": 7701,
            "NewRecovered": 0,
            "TotalRecovered": 0,
            "Date": "2022-05-31T18:37:38.127Z",
            "Premium": {}
        }
     */

    //String country, cases, todayCases, deaths, todayDeaths, recovered, todayRecovered;

    String Country, CountryCode, Slug, NewConfirmed, TotalConfirmed, NewDeaths, TotalDeaths, NewRecovered, TotalRecovered, Date;

    public ModelStat() {

    }

    public ModelStat(String country, String countryCode, String slug, String newConfirmed, String totalConfirmed, String newDeaths, String totalDeaths, String newRecovered, String totalRecovered, String date) {
        Country = country;
        CountryCode = countryCode;
        Slug = slug;
        NewConfirmed = newConfirmed;
        TotalConfirmed = totalConfirmed;
        NewDeaths = newDeaths;
        TotalDeaths = totalDeaths;
        NewRecovered = newRecovered;
        TotalRecovered = totalRecovered;
        Date = date;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getSlug() {
        return Slug;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public String getNewConfirmed() {
        return NewConfirmed;
    }

    public void setNewConfirmed(String newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public String getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public String getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        NewDeaths = newDeaths;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public String getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(String newRecovered) {
        NewRecovered = newRecovered;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
