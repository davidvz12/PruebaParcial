package com.example.countryinfo.Model;

public class Geognos {

    String Name,Capital,West,East,North,South,tld,iso3,iso2,fips,isoN,TelPref;

    public Geognos(String name, String capital, String west, String east, String north, String south, String tld, String iso3, String iso2, String fips, String isoN, String telPref) {
        this.Name = name;
        this.Capital = capital;
        this.West = west;
        this.East = east;
        this.North = north;
        this.South = south;
        this.tld = tld;
        this.iso3 = iso3;
        this.iso2 = iso2;
        this.fips = fips;
        this.isoN = isoN;
        this.TelPref = telPref;
    }

    public Geognos() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public String getWest() {
        return West;
    }

    public void setWest(String west) {
        West = west;
    }

    public String getEast() {
        return East;
    }

    public void setEast(String east) {
        East = east;
    }

    public String getNorth() {
        return North;
    }

    public void setNorth(String north) {
        North = north;
    }

    public String getSouth() {
        return South;
    }

    public void setSouth(String south) {
        South = south;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getFips() {
        return fips;
    }

    public void setFips(String fips) {
        this.fips = fips;
    }

    public String getIsoN() {
        return isoN;
    }

    public void setIsoN(String isoN) {
        this.isoN = isoN;
    }

    public String getTelPref() {
        return TelPref;
    }

    public void setTelPref(String telPref) {
        TelPref = telPref;
    }
}
