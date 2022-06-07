package com.alibaba.fastjson.support.geo;

import com.alibaba.fastjson.annotation.JSONType;
import java.util.LinkedHashMap;
import java.util.Map;

@JSONType(orders = {"type", "id", "bbox", "coordinates", "properties"}, typeName = "Feature")
/* loaded from: classes.dex */
public class Feature extends Geometry {
    private Geometry geometry;
    private String id;
    private Map<String, String> properties = new LinkedHashMap();

    public Feature() {
        super("Feature");
    }

    public Geometry getGeometry() {
        return this.geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, String> map) {
        this.properties = map;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }
}
