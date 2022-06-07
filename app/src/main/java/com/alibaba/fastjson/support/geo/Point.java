package com.alibaba.fastjson.support.geo;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"type", "bbox", "coordinates"}, typeName = "Point")
/* loaded from: classes.dex */
public class Point extends Geometry {
    private double[] coordinates;

    public Point() {
        super("Point");
    }

    public double[] getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(double[] dArr) {
        this.coordinates = dArr;
    }
}
