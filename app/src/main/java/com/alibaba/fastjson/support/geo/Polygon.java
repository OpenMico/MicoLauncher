package com.alibaba.fastjson.support.geo;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"type", "bbox", "coordinates"}, typeName = "Polygon")
/* loaded from: classes.dex */
public class Polygon extends Geometry {
    private double[][][] coordinates;

    public Polygon() {
        super("Polygon");
    }

    public double[][][] getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(double[][][] dArr) {
        this.coordinates = dArr;
    }
}
