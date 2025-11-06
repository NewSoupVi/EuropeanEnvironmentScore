package de.newsoupvi.environmentscore.model;

public enum SubtaskType {
    A("A", "Vegetation Analysis", "/data/vegetation.tif"),
    B("B", "Water Quality Assessment", "/data/water.tif"),
    C("C", "Air Quality Index", "/data/air.tif");

    private final String id;
    private final String description;
    private final String geotiffPath;

    SubtaskType(String id, String description, String geotiffPath) {
        this.id = id;
        this.description = description;
        this.geotiffPath = geotiffPath;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getGeotiffPath() {
        return geotiffPath;
    }
}

