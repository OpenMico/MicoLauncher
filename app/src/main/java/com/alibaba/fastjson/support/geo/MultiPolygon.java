package com.alibaba.fastjson.support.geo;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"type", "bbox", "coordinates"}, typeName = "MultiPolygon")
/* loaded from: classes.dex */
public class MultiPolygon extends Geometry {
    private double[][][][] coordinates;

    public MultiPolygon() {
        super("MultiPolygon");
    }

    public double[][][][] getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(double[][][][] dArr) {
        this.coordinates = dArr;
    }
}
