package com.asynchrony.rules.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Plan {
    private String id;
    private List<Requirement> requirements = new ArrayList<>();
    private List<GeoLocation> geoLocations = new ArrayList<>();

    public Plan() { }

    public Plan(String id, List<Requirement> requirements, List<GeoLocation> geoLocations) {
        this.id = id;
        this.requirements = requirements;
        this.geoLocations = geoLocations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public List<Requirement> addRequirements(Requirement... requirement){
        this.getRequirements().addAll(Arrays.asList(requirement));

        return this.getRequirements();
    }

    public void setRequirements(List<Requirement> requirements) {
        this.requirements = requirements;
    }

    public List<GeoLocation> addGeoLocations(GeoLocation... geoLocations){
        this.getGeoLocations().addAll(Arrays.asList(geoLocations));

        return this.getGeoLocations();
    }

    public List<GeoLocation> getGeoLocations() {
        return geoLocations;
    }

    public void setGeoLocations(List<GeoLocation> geoLocations) {
        this.geoLocations = geoLocations;
    }
}
