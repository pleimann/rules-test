package com.asynchrony.rules.model;

public class GeoLocation {
    private final String code;
    private GeoLocationType type;
    private String country;
    private String isoCode;

    public GeoLocation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public GeoLocationType getType() {
        return type;
    }

    public void setType(GeoLocationType type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}
