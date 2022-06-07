package com.alibaba.fastjson.support.geo;

import com.alibaba.fastjson.annotation.JSONType;
import java.util.ArrayList;
import java.util.List;

@JSONType(orders = {"type", "bbox", "coordinates"}, typeName = "FeatureCollection")
/* loaded from: classes.dex */
public class FeatureCollection extends Geometry {
    private List<Feature> features = new ArrayList();

    public FeatureCollection() {
        super("FeatureCollection");
    }

    public List<Feature> getFeatures() {
        return this.features;
    }
}
