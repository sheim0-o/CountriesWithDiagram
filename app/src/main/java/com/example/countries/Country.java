package com.example.countries;

public class Country {
    private String name;
    private String population;
    private String flag;

    public Country(String name, String population, String flag){
        this.name=name;
        this.population=population;
        this.flag=flag;
    }

    public String getNameCountry() {
        return this.name;
    }

    public void setNameCountry(String name) {
        this.name = name;
    }

    public String getPopulationCountry() {
        return this.population;
    }

    public void setPopulationCountry(String population) {
        this.population = population;
    }

    public String getFlagCountry() {
        return this.flag;
    }

    public void setFlagCountry(String flag) {
        this.flag = flag;
    }
}