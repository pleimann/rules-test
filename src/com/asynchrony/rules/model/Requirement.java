package com.asynchrony.rules.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.UUID;

public class Requirement {
    private UUID id;
    private Date earliestDate;
    private Date latestDate;
    private TransportMode mode;
    private String geoCode;
    private Boolean valid;

    public Requirement() {
    }

    public Requirement(UUID id) {
        this.id = id;
    }

    public Requirement(UUID id, Date earliestDate, Date latestDate, TransportMode mode, String geoCode) {
        this.id = id;
        this.earliestDate = earliestDate;
        this.latestDate = latestDate;
        this.mode = mode;
        this.geoCode = geoCode;
    }

    public Date getLatestDate() { return latestDate; }

    public void setLatestDate(Date latestDate) { this.latestDate = latestDate; }

    public TransportMode getMode() { return mode; }

    public void setMode(TransportMode mode) { this.mode = mode; }

    public String getGeoCode() { return this.geoCode; }

    public void setGeoCode(String geoCode) { this.geoCode = geoCode; }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) { this.id = id; }

    public Date getEarliestDate() {
        return earliestDate;
    }

    public void setEarliestDate(Date earliestDate) {
        this.earliestDate = earliestDate;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
